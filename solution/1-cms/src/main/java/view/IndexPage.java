package view;


import io.mvnpm.jeans.Element;
import io.mvnpm.jeans.Html;
import io.quarkiverse.renarde.router.Router;
import model.BlogEntry;
import rest.Cms;

import java.net.URI;

import static io.mvnpm.jeans.Html.*;

public class IndexPage extends Element {

    public BlogEntry currentBlogEntry;

    @Override
    public Html render() {
        URI newBlogEntryUri = Router.getURI(Cms::newBlogEntry);
        // language=html
        return html("""
                <base-page title="Quarkus Blogger CMS">
                  <div class="left-bar d-flex justify-content-between">
                    <div class="p-2">
                      <div class="mb-3 d-flex">
                        <a
                          class="btn btn-outline-dark"
                          href="%s"
                          hx-get="%s"
                          hx-push-url="true"
                          hx-target="#blog-editor"
                        ><i class="bi bi-plus"></i> Post</a>
                      </div>
                      <blog-entries />
                    </div>
                    <div id="blog-editor" class="flex-grow-1 p-2">
                      %s
                    </div>
                  </div>
                </base-page>
                """
                .formatted(
                        newBlogEntryUri,
                        newBlogEntryUri,
                        this.renderEditor()
                ));
    }

    private Html renderEditor() {
        // language=html
        return currentBlogEntry != null ? html("""
                    <blog-editor entry="%s"  />
                """.formatted(typed(currentBlogEntry))) : empty();
    }
}
