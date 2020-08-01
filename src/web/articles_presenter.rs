use crate::web::route_builder::RouteBuilder;
use actix_web::web;

use crate::articles::ArticlesComponent;
use actix_web::web::{Json, Payload};
use actix_web::FromRequest;
use actix_web::{HttpRequest, HttpResponse};

use std::sync::Arc;

use crate::models::Article;
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
        routes![self,
            ("/articles", get, get_articles),
            ("/articles/{id}", get, get_article),
            ("/articles/{id}", put, modify_article)
        ]
    }
    async fn get_articles(self: Arc<Self>, _: HttpRequest, _: Payload) -> HttpResponse {
        let result = self.articles_component.get_articles();
        HttpResponse::Ok().json(ResponseBody::ok(result))
    }
    async fn get_article(self: Arc<Self>, request: HttpRequest, _: Payload) -> HttpResponse {
        let params: Result<u32, _> = request.match_info().load();
        let id = match params {
            Ok(id) => id,
            Err(_) => return HttpResponse::NotFound().finish(),
        };
        match self.articles_component.get_article(id) {
            Some(article) => HttpResponse::Ok().json(ResponseBody::ok(article)),
            None => HttpResponse::NotFound().finish(),
        }
    }
    async fn modify_article(
        self: Arc<Self>,
        request: HttpRequest,
        payload: Payload,
    ) -> HttpResponse {
        let article = Json::<Article>::from_request(&request, &mut payload.into_inner());
        let params: Result<u32, _> = request.match_info().load();
        let id = match params {
            Ok(id) => id,
            Err(_) => return HttpResponse::NotFound().finish(),
        };
        let mut article = match article.await {
            Ok(article) => article.into_inner(),
            Err(_) => return HttpResponse::NotFound().finish(),
        };
        article.id = id;
        match self.articles_component.modify_article(&article) {
            true => HttpResponse::Ok().json(ResponseBody::ok(true)),
            false => return HttpResponse::NotFound().finish(),
        }
    }
}
