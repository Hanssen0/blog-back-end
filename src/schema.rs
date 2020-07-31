table! {
    accounts (id) {
        id -> Unsigned<Integer>,
        username -> Nullable<Varchar>,
        password -> Nullable<Text>,
    }
}

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

allow_tables_to_appear_in_same_query!(
    accounts,
    articles,
);
