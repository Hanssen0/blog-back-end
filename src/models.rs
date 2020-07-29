use serde::Deserialize;
use serde::Serialize;
use diesel::Queryable;
#[derive(Queryable, Deserialize, Serialize)]
pub struct Article {
    pub id: u32,
    pub title: Option<String>,
    pub subtitle: Option<String>,
    pub content: Option<String>,
    pub author: Option<u32>,
    pub publish_time: Option<u64>,
}
