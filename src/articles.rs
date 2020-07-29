use diesel::connection::Connection;
use diesel::mysql::Mysql;
use diesel::query_dsl::RunQueryDsl;

use crate::models::Article;
use crate::schema::articles;

use std::marker::PhantomData;

pub struct ArticlesComponentFactory<'a, C, P> where
        P: (Fn() -> C) + Clone + Sync + Send + 'a,
        C: Connection<Backend = Mysql> + 'a {
    pool: Option<P>,
    _phantom: PhantomData<&'a C>
}
impl<'a, C, P> ArticlesComponentFactory<'a, C, P> where
        P: (Fn() -> C) + Clone + Sync + Send + 'a,
        C: Connection<Backend = Mysql> + 'a {
    pub fn new() -> ArticlesComponentFactory<'a, C, P> {
        ArticlesComponentFactory{ pool: None, _phantom: PhantomData }
    }
    pub fn pool(self, pool: P) -> Self {
        ArticlesComponentFactory{ pool: Some(pool), ..self }
    }
    pub fn build(&self) -> impl ArticlesComponent + Sync + Send + 'a {
        let pool = self.pool.clone();
        let pool = match pool {
            None => panic!("No pool specified for ArticlesComponent"),
            Some(pool) => pool.clone()
        };
        DatabaseArticlesComponent {
            repository: MySQLArticlesRepository { pool: pool , _phantom: PhantomData, },
            _phantom: PhantomData,
        }
    }
}

pub trait ArticlesComponent {
    fn get_articles(&self) -> Vec<Article>;
}

struct DatabaseArticlesComponent<'a, R: ArticlesRepository + Sync + Send + 'a> {
    repository: R,
    _phantom: PhantomData<&'a R>,
}
impl<'a, R: ArticlesRepository + Sync + Send + 'a> ArticlesComponent
        for DatabaseArticlesComponent<'a, R> {
    fn get_articles(&self) -> Vec<Article> {
        self.repository.query_articles()
    }
}

trait ArticlesRepository {
    fn query_articles(&self) -> Vec<Article>;
}

struct MySQLArticlesRepository<'a, T, C> where
        T: Fn() -> C + Sync + Send + 'a,
        C: Connection<Backend = Mysql> {
    pool: T,
    _phantom: PhantomData<&'a T>
}
impl<'a, T, C> ArticlesRepository for MySQLArticlesRepository<'a, T, C> where
        T: Fn() -> C + Sync + Send + 'a,
        C: Connection<Backend = Mysql> {
    fn query_articles(&self) -> Vec<Article> {
        let connection = (self.pool)();
        articles::table
            .load::<Article>(&connection)
            .expect("Error loading articles")
    }
}
