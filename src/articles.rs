use crate::models::Article;
use diesel::mysql::MysqlConnection;
use diesel::query_dsl::RunQueryDsl;
use crate::schema::articles;

pub struct ArticlesComponentFactory {
    factory: ArticlesRepositoryFactory,
}
impl ArticlesComponentFactory {
    pub fn new() -> ArticlesComponentFactory {
        ArticlesComponentFactory{ factory: ArticlesRepositoryFactory {} }
    }
    pub fn build(&self) -> impl ArticlesComponent {
        DatabaseArticlesComponent {
            repository: self.factory.build()
        }
    }
}

pub trait ArticlesComponent {
    fn get_articles(&self) -> Vec<Article>;
}

struct DatabaseArticlesComponent {
    repository: Box<dyn ArticlesRepository>,
}
impl ArticlesComponent for DatabaseArticlesComponent {
    fn get_articles(&self) -> Vec<Article> {
        self.repository.query_articles()
    }
}

struct ArticlesRepositoryFactory {
}
impl ArticlesRepositoryFactory {
    pub fn build(&self) -> Box<dyn ArticlesRepository> {
        Box::new(MySQLArticlesRepository {
            // TODO: Connection pool
            connection: crate::establish_connection()
        })
    }
}

trait ArticlesRepository {
    fn query_articles(&self) -> Vec<Article>;
}

struct MySQLArticlesRepository {
    connection: MysqlConnection,
}
impl ArticlesRepository for MySQLArticlesRepository {
    fn query_articles(&self) -> Vec<Article> {
        articles::table
            .load::<Article>(&self.connection)
            .expect("Error loading articles")
    }
}
