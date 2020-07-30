use crate::web::route_builder::RouteBuilder;
use actix_web::web;

use crate::articles::ArticlesComponent;
use actix_web::{HttpRequest, HttpResponse};

use std::sync::Arc;

use crate::response_body::ResponseBody;

pub struct ArticlesPresenter<C: ArticlesComponent + Sync + Send + 'static> {
    articles_component: C,
}
impl<C: ArticlesComponent + Sync + Send + 'static> ArticlesPresenter<C> {
    pub fn new(articles_component: C) -> ArticlesPresenter<C> {
        ArticlesPresenter {
            articles_component: articles_component,
        }
    }
    pub fn get_routes(self) -> Vec<RouteBuilder> {
        let arc = Arc::new(self);
        let arc_clone_1 = arc.clone();
        let arc_clone_2 = arc.clone();
        vec![
            RouteBuilder::new("/articles".to_string(), web::get, move |_: &HttpRequest| {
                arc_clone_1.get_articles()
            }),
            RouteBuilder::new(
                "/articles/{id}".to_string(),
                web::get,
                move |request: &HttpRequest| arc_clone_2.get_article(request),
            ),
        ]
    }
    fn get_articles(&self) -> HttpResponse {
        let result = self.articles_component.get_articles();
        HttpResponse::Ok().json(ResponseBody::ok(result))
    }
    fn get_article(&self, request: &HttpRequest) -> HttpResponse {
        let params: Result<u32, _> = request.match_info().load();
        let id = match params {
            Ok(id) => id,
            Err(msg) => return HttpResponse::Ok().body(msg.to_string()),
        };
        HttpResponse::Ok().json(ResponseBody::ok(self.articles_component.get_article(id)))
    }
}
