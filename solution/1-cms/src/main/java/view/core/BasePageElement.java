package view.core;

import io.mvnpm.jeans.El;
import io.mvnpm.jeans.Element;
import io.mvnpm.jeans.Html;

import static io.mvnpm.jeans.Html.html;
import static io.mvnpm.jeans.Html.raw;

@El
public class BasePageElement extends Element {

    public String title;

    public boolean bundle = true;


    @Override
    public Html render() {
        // language=html
        return raw("""
                <!DOCTYPE html>
                <html>
                <head>
                <title>%s</title>
                %s
                </head>
                <body>
                  %s
                </body>
                </html>
                                
                """.formatted(title, bundle ? html("<bundle />"): "", title, html(slot())));
    }
}
