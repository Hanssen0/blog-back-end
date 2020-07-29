use serde::Serialize;
#[derive(Serialize)]
pub struct ResponseBody<T: Serialize> {
    error_type: Option<String>,
    error_message: Option<String>,
    data: Option<T>,
}
impl<T: Serialize> ResponseBody<T> {
    pub fn ok(data: T) -> Self {
        Self {
            error_type: None,
            error_message: None,
            data: Some(data),
        }
    }
}
