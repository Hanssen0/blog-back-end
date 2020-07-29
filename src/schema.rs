table! {
    articles (id) {
        id -> Unsigned<Integer>,
        title -> Nullable<Text>,
        subtitle -> Nullable<Text>,
        content -> Nullable<Text>,
        author -> Nullable<Unsigned<Integer>>,
        publish_time -> Nullable<Unsigned<Bigint>>,
    }
}
