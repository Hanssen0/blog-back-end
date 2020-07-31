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
        let self_arc = Arc::new(self);
        let self_arc_1 = self_arc.clone();
        let self_arc_2 = self_arc.clone();
        let self_arc_3 = self_arc.clone();
        vec![
            RouteBuilder::new(
                "/articles".to_string(),
                web::get,
                move |_: HttpRequest, _: Payload| Box::pin(self_arc_1.clone().get_articles()),
            ),
            RouteBuilder::new(
                "/articles/{id}".to_string(),
                web::get,
                move |request: HttpRequest, _: Payload| {
                    Box::pin(self_arc_2.clone().get_article(request))
                },
            ),
            RouteBuilder::new(
                "/articles/{id}".to_string(),
                web::put,
                move |request: HttpRequest, payload: Payload| {
                    Box::pin(self_arc_3.clone().modify_article(request, payload))
                },
            ),
        ]
    }
    async fn get_articles(self: Arc<Self>) -> HttpResponse {
        let result = self.articles_component.get_articles();
        HttpResponse::Ok().json(ResponseBody::ok(result))
    }
    async fn get_article(self: Arc<Self>, request: HttpRequest) -> HttpResponse {
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
