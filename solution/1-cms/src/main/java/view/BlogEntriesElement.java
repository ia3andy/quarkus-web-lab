package view;

import io.mvnpm.jeans.El;
import io.mvnpm.jeans.Element;
import io.mvnpm.jeans.Html;
import io.quarkiverse.renarde.router.Router;
import model.BlogEntry;
import rest.Cms;

import java.net.URI;
import java.util.List;

import static io.mvnpm.jeans.Html.*;

@El
public class BlogEntriesElement extends Element {
    public List<BlogEntry> blogEntries;
    public BlogEntry currentBlogEntry;

    @Override
    public Html render() {
        // language=html
        return html("""
                <ul id="blogEntries" hx-swap-oob="true" class="blogEntries list-group">
                  %s
                </ul>
            """.formatted(map(blogEntries, this::renderEntry)));
    }

    private Html renderEntry(BlogEntry entry) {
        URI editBlogEntryUri = Router.getURI(Cms::editBlogEntry, entry.id);
       // language=html
        return html("""
                <li class="%s list-group-item blogEntry d-flex justify-content-between align-items-center" >
                    <a href="%s" hx-get="%s" hx-push-url="true" hx-target="#blog-editor" hx-swap="innerHTML">%s: %s</a>
                    %s
                </li>
            """.formatted(
            className(currentBlogEntry.id.equals(entry.id), "active"),
            editBlogEntryUri, editBlogEntryUri,
            entry.published, entry.title,
            this.renderDeleteButton(entry)
        ));
    }

    private Html renderDeleteButton(BlogEntry entry) {
        // language=html
        return showIf(entry.id != null, () -> raw("""
                <button class="btn blogEntry-delete"
                        hx-delete="%s"
                        hx-confirm="Are you sure?"
                        hx-target="closest .blogEntry"
                        hx-swap="outerHTML swap:0.5s"
                />
                    <i class="bi bi-trash"></i>
                </button>
            """.formatted(Router.getURI(Cms::deleteBlogEntry, entry.id))));
    }
}
