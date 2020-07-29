CREATE TABLE articles (
  id INT UNSIGNED AUTO_INCREMENT,
  title TEXT,
  subtitle TEXT,
  content TEXT,
  author INT UNSIGNED,
  publish_time BIGINT UNSIGNED,
  PRIMARY KEY (id)
);
