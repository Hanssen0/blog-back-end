macro_rules! pin_self {
    ($path: expr, $method: ident, $self_arc: expr, $handler: ident) => {{
        let clone = $self_arc.clone();
        RouteBuilder::new(
            "/accounts",
            web::$method,
            move |request: HttpRequest, payload: Payload| Box::pin(clone.clone().$handler(request, payload))
        )
    }}
}
macro_rules! routes {
    [$this: expr, $(($path: expr, $method: ident, $handler: ident)),*] => {{
        let arc = Arc::new($this);
        vec![
            $(pin_self!($path, $method, arc, $handler),)*
        ]
    }}
}
