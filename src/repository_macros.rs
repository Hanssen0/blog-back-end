macro_rules! query {
    ($connection: expr, $schema: ident, $result_type: ident) => {{
        match $schema::table
            .load::<$result_type>(&$connection) {
            Ok(result) => result,
            Err(_) => panic!("Error occurs while loading {} from {}", stringify!($result_type), stringify!($table))
        }
    }}
}
macro_rules! query_by_id {
    ($connection: expr, $schema: ident, $result_type: ident, $id: expr) => {{
        $schema::table
            .find($id)
            .get_result::<$result_type>(&$connection)
            .ok()
    }}
}
macro_rules! update {
    ($connection: expr, $schema: ident, $result_type: ident, $entity: expr) => {{
        match diesel::update($schema::table)
            .filter($schema::id.eq($entity.id))
            .set($entity)
            .execute(&$connection)
        {
            Ok(1) => true,
            Ok(0) => false,
            Ok(n) => panic!(format!("Wrong number of updated {} in {}: {}", stringify!($result_type), stringify!($schema), n)),
            Err(msg) => panic!(
                "Error occurs while updating {} from {}: {}", stringify!($result_type), stringify!($schema), msg.to_string()
            ),
        }
    }}
}
