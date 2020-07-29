use actix_cors::Cors;
use actix_web::{App, HttpServer};
use diesel::mysql::MysqlConnection;
use diesel::r2d2::{ConnectionManager, Pool};
use std::sync::Arc;

use blog_back_end::web::route_builder::RouteBuilder;

use blog_back_end::articles::ArticlesComponentFactory;
use blog_back_end::web::articles_presenter::ArticlesPresenter;

trait RouteBuilderVector {
    fn merge(self, source: Self) -> Self;
}
impl RouteBuilderVector for Vec<RouteBuilder> {
    fn merge(mut self, source: Self) -> Self {
        for route in source {
            self.push(route);
        }
        self
    }
}

#[actix_rt::main]
async fn main() -> std::io::Result<()> {
    let manager: ConnectionManager<MysqlConnection> =
        ConnectionManager::new("mysql://root@localhost/blog");
    let pool = Pool::builder().max_size(10).build(manager).unwrap();

    let pool_clone = pool.clone();
    let article_routes = ArticlesPresenter::new(
        ArticlesComponentFactory::new()
            .pool(move || pool_clone.get().unwrap())
            .build(),
    );

    let http_routes_arc = Arc::new(vec![].merge(article_routes.get_routes()));

    HttpServer::new(move || {
        let mut app = App::new().wrap(
            Cors::new()
                .supports_credentials()
                .allowed_origin("http://localhost:8080")
                .finish(),
        );
        for route in http_routes_arc.iter() {
            app = app.service(route.build());
        }
        app
    })
    .bind("127.0.0.1:8090")?
    .run()
    .await
}
