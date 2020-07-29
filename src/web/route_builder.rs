use actix_web::{ Route, HttpRequest, HttpResponse, Resource, web };
use std::sync::Arc;

pub type RouteHandlerArc = Arc<dyn Fn(&HttpRequest) -> HttpResponse + Sync + Send + 'static>;
pub struct RouteBuilder {
    uri: String,
    route: Box<dyn Fn() -> Route + Sync + Send + 'static>,
    handler: RouteHandlerArc,
}
impl RouteBuilder {
    pub fn new(
            uri: String,
            route: impl Fn() -> Route + Sync + Send + 'static,
            handler: impl Fn(&HttpRequest) -> HttpResponse + Sync + Send + 'static)
            -> RouteBuilder{
        RouteBuilder { uri: uri, route: Box::new(route), handler: Arc::new(handler) }
    }
    pub fn decorate(
            self, decorator : impl Fn(RouteHandlerArc) -> RouteHandlerArc)
            -> RouteBuilder {
        RouteBuilder { handler: decorator(self.handler), ..self }
    }
    pub fn build(&self) -> Resource {
        let handler = self.handler.clone();
        web::resource(&(self.uri)).route((self.route)().to(
            move |request: HttpRequest| {
                let handler_for_async = handler.clone();
                async move { handler_for_async(&request) }
            }
        ))
    }
}
