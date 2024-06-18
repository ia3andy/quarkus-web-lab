package view;

import io.mvnpm.jeans.El;
import io.mvnpm.jeans.Element;
import io.mvnpm.jeans.Html;
import io.quarkiverse.renarde.router.Router;
import io.quarkiverse.renarde.util.Flash;
import io.quarkiverse.renarde.util.Validation;
import jakarta.inject.Inject;
import model.BlogEntry;
import rest.Cms;

import java.net.URI;

import static io.mvnpm.jeans.Html.*;

@El
public class BlogEditorElement extends Element {
    public BlogEntry entry;

    @Override
    public Html render() {

        URI postLink = entry.id != null ?
                Router.getURI(Cms::saveBlogEntry, entry.id) : Router.getURI(Cms::saveNewBlogEntry);
        // language=html
        return html("""
                 <form
                     hx-post="%s"
                     class="blogEntry-form"
                     hx-encoding='multipart/form-data'
                     hx-target="this"
                     hx-push-url="true"
                 >
                     <div class="editor-wrapper">
                         <div class="mb-3">
                            <editor-field name="title" value="%s" placeholder="Entre title" />
                         </div>
                         <div class="mb-3">
                             <editor-field name="published" value="%s" />
                         </div>
                         <div class="mb-3">
                            <editor-field name="picture" value="%s" placeholder="Entre picture name" />
                         </div>
                         <div class="mb-3">
                            <editor-field id="blogEntry-content" name="content">
                                <blog-rich-editor id="blogEntry-content" name="content" value="%s" />
                            </editor-field>
                         </div>
                     </div>
                     <button class="btn btn-primary">Save</button>
                 </form>
                """.formatted(
                        postLink,
                        entry.title, entry.published, entry.content));
    }


    @El
    public static class EditorFieldElement extends Element {
        
        public String name;
        public String value;
        public String placeholder = "";


        @Inject
        public Validation validation;

        @Inject
        public Flash flash;

        public Html render() {
            final String flashValue = flash.get(name);
            String value = flashValue != null ? flashValue : this.value;
            // language=html
            return html("""
                    <div class="mb-3">
                        %s
                        %s
                    </div>
                    """.formatted(
                    this.renderInput(),
                    showIf(validation.hasError(name), this::renderError)
            ));
        }

        private String renderInput() {
            // language=html
            return hasSlot() ? slot() : """
                    <input name="picture" class="form-control %s" value="%s" placeholder="%s" required/>
                    """.formatted(className(validation.hasError(name), "is-invalid"), name, placeholder, value);
        }

        private Html renderError() {
            // language=html
            return raw("""
                    <div class="invalid-feedback">
                      Error: %s
                    </div>
                     """.formatted(validation.getError(name)));
        }


    }

}
