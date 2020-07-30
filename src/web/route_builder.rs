use actix_web::{web, HttpRequest, HttpResponse, Resource, Route};
use std::future::Future;
use std::pin::Pin;
use std::sync::Arc;

pub type ArcRouteHandler = Arc<
    dyn (Fn(HttpRequest) -> Pin<Box<dyn Future<Output = HttpResponse>>>) + Sync + Send + 'static,
>;
pub struct RouteBuilder {
    uri: String,
    route: Box<dyn Fn() -> Route + Sync + Send + 'static>,
    handler: ArcRouteHandler,
}
impl RouteBuilder {
    pub fn new(
        uri: String,
        route: impl Fn() -> Route + Sync + Send + 'static,
        handler: impl (Fn(HttpRequest) -> Pin<Box<dyn Future<Output = HttpResponse>>>)
            + Sync
            + Send
            + 'static,
    ) -> RouteBuilder {
        RouteBuilder {
            uri: uri,
            route: Box::new(route),
            handler: Arc::new(handler),
        }
    }
    pub fn decorate(
        mut self,
        decorator: &impl Fn(ArcRouteHandler) -> ArcRouteHandler,
    ) -> RouteBuilder {
        self.handler = decorator(self.handler);
        self
    }
    pub fn build(&self) -> Resource {
        let handler = self.handler.clone();
        web::resource(&(self.uri))
            .route((self.route)().to(move |request: HttpRequest| handler(request)))
    }
}
