use crate::schema::articles;
use crate::schema::accounts;
use diesel::AsChangeset;
use diesel::Queryable;
use serde::Deserialize;
use serde::Serialize;
#[derive(Queryable, AsChangeset, Deserialize, Serialize)]
#[table_name = "articles"]
pub struct Article {
    #[serde(default)]
    pub id: u32,
    pub title: Option<String>,
    pub subtitle: Option<String>,
    pub content: Option<String>,
    pub author: Option<u32>,
    pub publish_time: Option<u64>,
}
#[derive(Queryable, AsChangeset, Deserialize, Serialize)]
#[table_name = "accounts"]
pub struct Account {
    #[serde(default)]
    pub id: u32,
    pub username: Option<String>,
    pub password: Option<String>,
}
