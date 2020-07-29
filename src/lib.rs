#[macro_use] extern crate diesel;
use diesel::connection::Connection;
use diesel::mysql::MysqlConnection;

pub mod models;
pub mod schema;
pub mod web;
pub mod articles;

pub fn establish_connection() -> MysqlConnection {
    let database_url = "mysql://root:@localhost/blog";
    MysqlConnection::establish(&database_url)
        .expect(&format!("Error connecting to {}", database_url))
}
