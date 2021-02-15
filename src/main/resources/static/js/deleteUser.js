//DELETE USER
function DeleteUserJson(id) {
    console.log("Функция DeleteUserJson, переданный параметр: "+id);
    return fetch('http://localhost:8088/admin/users/'+id, {
        method: "delete"
    })
        .then(response => {
            if(response.ok){
                return response.json()
            } else {
                throw Error
            }
        })
}
function DeleteUser(user) {
    $('form[id="del-'+user.id+'"]').submit(function (e) {
        e.preventDefault();

        DeleteUserJson(user.id);
        $(".modal").modal("hide");
        listUsers($);
    });
}