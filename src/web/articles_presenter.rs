use actix_web::web;
use crate::web::route_builder::RouteBuilder;

pub fn get_routes() -> Vec<RouteBuilder> {
    vec![
        RouteBuilder::new("/articles".to_string(), web::get, get_articles)
    ]
}

use actix_web::{ HttpRequest, HttpResponse };
use crate::articles::ArticlesComponent;
use crate::articles::ArticlesComponentFactory;

fn get_articles(_: &HttpRequest) -> HttpResponse {
    let factory = ArticlesComponentFactory::new();
    let component = factory.build();
    let result = component.get_articles();
    HttpResponse::Ok().json(result)
}
