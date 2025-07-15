async  function signUp() {

    const firstname = document.getElementById("firstname").value;
    const lastname = document.getElementById("lastname").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

//    console.log(firstname);
//    console.log(lastname);
//    console.log(email);
//    console.log(password);

    // User Object
    const user = {

        firstname: firstname,
        lastname: lastname,
        email: email,
        password: password

    };

    // Json Objet
    const userJson = JSON.stringify(user);

    //Fetch
    const response = await fetch(
            "SignUp", // URL
            {

                method: "POST",
                body: userJson,
                header: {
                    "Content-Type": "application/json"
                }

            }

    ); //await use karanawanam async wenna one function eka

}

