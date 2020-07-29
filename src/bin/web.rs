use actix_web::{ App, HttpServer };
use std::sync::Arc;
use blog_back_end::web::articles_presenter;

#[actix_rt::main]
async fn main() -> std::io::Result<()> {
    let mut http_routes = vec![];
    let routes = articles_presenter::get_routes();
    for route in routes { http_routes.push(route); }
    let http_routes_arc = Arc::new(http_routes);
    HttpServer::new(
        move || {
            let mut app = App::new();
            for route in http_routes_arc.iter() {
                app = app.service(route.build());
            }
            app
        })
    .bind("127.0.0.1:8090")?
        .run()
        .await
}
