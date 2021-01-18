$(function () {
    fetch('http://localhost:8133/api/users')
        .then(response => response.json())
        .then(persons => persons.forEach(function (person) {
                $('tbody#main-body-table').append(`
                        <tr id="${person.id}">
                            <td name="id">${person.id}</td>
                            <td name="firstName">${person.firstName}</td>
                            <td name="lastName">${person.lastName}</td>
                            <td name="age">${person.age}</td>
                            <td name="email">${person.email}</td>
                            <td name="roles"></td>
                        </tr>
                    `)
                person.roles.forEach(function (role) {
                    $('tbody#main-body-table td:last').append(`
                        <span>${role} </span>
                        `)
                })
                $('tbody#main-body-table tr#' + person.id).append(`
                        <td>
                            <!-- Button edit trigger modal -->
                            <button name="edit" id="edit-${person.id}" type="button" class="btn btn-info" data-toggle="modal" data-target="#modalEdit">
                                Edit
                            </button>
                        </td>
                        <td>
                            <!-- Button delete trigger modal -->
                            <button name="delete" id="delete-${person.id}" type="button" class="btn btn-danger" data-toggle="modal" data-target="#modalDelete">
                                Delete
                            </button>
                        </td>
                    `)

            })
        )
        .then(function () {
            $("button[name='edit']").bind( "click", function(e) {
                let btn = e.target
                let id = btn.getAttribute("id").replace("edit-", "")
                fetch('http://localhost:8133/api/users/' + id)
                    .then(response => response.json())
                    .then(person => {
                        $("div#modalEdit input[name='id']").val(id);
                        $("div#modalEdit input[name='firstName']").val(person.firstName);
                        $("div#modalEdit input[name='lastName']").val(person.lastName);
                        $("div#modalEdit input[name='age']").val(person.age);
                        $("div#modalEdit input[name='email']").val(person.email);
                        $("div#modalEdit option").prop('selected', false);
                        for (const role of person.roles) {
                            $("div#modalEdit option#" + role).prop('selected', true);
                        }
                    })
            })
            $("button[name='delete']").bind( "click", function(e) {
                let btn = e.target
                let id = btn.getAttribute("id").replace("delete-", "")
                fetch('http://localhost:8133/api/users/' + id)
                    .then(response => response.json())
                    .then(person => {
                        $("div#modalDelete input[name='id']").val(id);
                        $("div#modalDelete input[name='firstName']").val(person.firstName);
                        $("div#modalDelete input[name='lastName']").val(person.lastName);
                        $("div#modalDelete input[name='age']").val(person.age);
                        $("div#modalDelete input[name='email']").val(person.email);
                        $("div#modalDelete option").prop('selected', false);
                        for (const role of person.roles) {
                            $("div#modalDelete option#" + role).prop('selected', true);
                        }
                    })
            })
        })

    $('#button-edit').bind( "click", async function(e) {
        e.preventDefault();

        let id = $("div#modalEdit input[name='id']").val()
        let firstName = $("div#modalEdit input[name='firstName']").val()
        let lastName = $("div#modalEdit input[name='lastName']").val()
        let age = $("div#modalEdit input[name='age']").val()
        let email = $("div#modalEdit input[name='email']").val()
        let password = $("div#modalEdit input[name='password']").val()
        let roles = $("div#modalEdit select[name='roles']").val()
        let data = {
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName,
            age: age,
            roles: roles
        }

        let response = await fetch('http://localhost:8133/api/users/' + id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(data)
        });

        let result = await response.json();

        $("tbody#main-body-table tr#" + id + " td[name='firstName']").html(result.firstName)
        $("tbody#main-body-table tr#" + id + " td[name='lastName']").html(result.lastName)
        $("tbody#main-body-table tr#" + id + " td[name='age']").html(result.age)
        $("tbody#main-body-table tr#" + id + " td[name='email']").html(result.email)
        $("tbody#main-body-table tr#" + id + " td[name='roles']").empty()
        for (const role of result.roles) {
            $("tbody#main-body-table tr#" + id + " td[name='roles']").append(`
                    <span>${role} </span>
                `)
        }

        $("div#modalEdit").modal('hide');
    })

    $('#button-delete').bind( "click",async function(e) {
        e.preventDefault()

        let id = $("div#modalDelete input[name='id']").val()

        let response = await fetch('http://localhost:8133/api/users/' + id, {
            method: 'DELETE'
        });

        $("tbody#main-body-table tr#" + id).remove()

        $("div#modalDelete").modal('hide');
    })

    $('#button-add').bind( "click",async function(e) {
        e.preventDefault()

        let firstName = $("#firstName-add").val()
        let lastName = $("#lastName-add").val()
        let age = $("#age-add").val()
        let email = $("#email-add").val()
        let password = $("#password-add").val()
        let roles = $("#listRoles-add").val()
        let data = {
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName,
            age: age,
            roles: roles
        }

        let response = await fetch("http://localhost:8133/api/users", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(data)
        })

        let result = await response.json()

        $('tbody#main-body-table').append(`
                <tr id="${result.id}">
                    <td name="id">${result.id}</td>
                    <td name="firstName">${result.firstName}</td>
                    <td name="lastName">${result.lastName}</td>
                    <td name="age">${result.age}</td>
                    <td name="email">${result.email}</td>
                    <td name="roles"></td>
                </tr>
            `)

        result.roles.forEach(function (role) {
            $("tbody#main-body-table tr#" + result.id + " td[name='roles']").append(`
                        <span>${role} </span>
                `)
        })

        $('tbody#main-body-table tr#' + result.id).append(`
                <td>
                    <!-- Button edit trigger modal -->
                    <button name="edit" id="edit-${result.id}" type="button" class="btn btn-info" data-toggle="modal" data-target="#modalEdit">
                        Edit
                    </button>
                </td>
                <td>
                    <!-- Button delete trigger modal -->
                    <button name="delete" id="delete-${result.id}" type="button" class="btn btn-danger" data-toggle="modal" data-target="#modalDelete">
                        Delete
                    </button>
                </td>
            `)

        $("button#edit-" + result.id).bind( "click", function(e) {
            fetch('http://localhost:8133/api/users/' + result.id)
                .then(response => response.json())
                .then(person => {
                    $("div#modalEdit input[name='id']").val(result.id);
                    $("div#modalEdit input[name='firstName']").val(person.firstName);
                    $("div#modalEdit input[name='lastName']").val(person.lastName);
                    $("div#modalEdit input[name='age']").val(person.age);
                    $("div#modalEdit input[name='email']").val(person.email);
                    $("div#modalEdit option").prop('selected', false);
                    for (const role of person.roles) {
                        $("div#modalEdit option#" + role).prop('selected', true);
                    }
                })
        })

        $("button#delete-" + result.id).bind( "click", function(e) {
            fetch('http://localhost:8133/api/users/' + result.id)
                .then(response => response.json())
                .then(person => {
                    $("div#modalDelete input[name='id']").val(result.id);
                    $("div#modalDelete input[name='firstName']").val(person.firstName);
                    $("div#modalDelete input[name='lastName']").val(person.lastName);
                    $("div#modalDelete input[name='age']").val(person.age);
                    $("div#modalDelete input[name='email']").val(person.email);
                    $("div#modalDelete option").prop('selected', false);
                    for (const role of person.roles) {
                        $("div#modalDelete option#" + role).prop('selected', true);
                    }
                })
        })

        $("#home-tab").tab('show');

        $("#firstName-add").val("")
        $("#lastName-add").val("")
        $("#age-add").val("")
        $("#email-add").val("")
        $("#password-add").val("")
        $("#listRoles-add option").prop('selected', false);
    })

})