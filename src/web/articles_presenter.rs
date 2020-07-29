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
        vec![RouteBuilder::new(
            "/articles".to_string(),
            web::get,
            move |_: &HttpRequest| arc_clone_1.get_articles(),
        )]
    }
    fn get_articles(&self) -> HttpResponse {
        let result = self.articles_component.get_articles();
        HttpResponse::Ok().json(ResponseBody::ok(result))
    }
}
