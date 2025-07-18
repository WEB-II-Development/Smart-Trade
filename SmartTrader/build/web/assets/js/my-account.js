//window.onload = function () {
//
//    alert("ok");
//
//}

//window.addEventListener("load", async function () {
//
//    const  response = await fetch("MyAccount");
//
//});

function loadData() {

    getUserData();
    getCityData();

}

async function getUserData() {

    const  response = await fetch("MyAccount");

    if (response.ok) {

        const json = await response.json();

        console.log(json);

        document.getElementById("username").innerHTML = `Hello, ${json.firstname} ${json.lastname}`;

        document.getElementById("since").innerHTML = `Smart Trade Member Since ${json.since}`;

        document.getElementById("firstname").value = json.firstname;

        document.getElementById("lastname").value = json.lastname;

        document.getElementById("currentpassword").value = json.password;

        if (json.hasOwnProperty("addressList") && json.addressList !== undefined) {

            let email;

            let lineOne;

            let lineTwo;

            let city;

            let postalCode;

            let cityId;

            const addressUL = document.getElementById("addressUL");

            json.addressList.forEach(address => {

                email = address.user.email;

                lineOne = address.lineOne;

                lineTwo = address.lineTwo;

                city = address.city.name;

                postalCode = address.postalCode;

                cityId = address.city.id;

                const line = document.createElement("li");

                line.innerHTML = lineOne + ", " + "<br/>" +
                        lineTwo + ", " + "<br/>" + city + ". " + "<br/>" + postalCode;

                addressUL.appendChild(line);

            });

//            console.log("ok");

            document.getElementById("addName").innerHTML = `Name : ${json.firstname} ${json.lastname}`;

            document.getElementById("addEmail").innerHTML = `Email : ${email}`;

            document.getElementById("contact").innerHTML = `Phone : 0711401506`;

//            console.log(lastAddress);

            document.getElementById("lineOne").value = lineOne;

            document.getElementById("lineTwo").value = lineTwo;

            document.getElementById("postalCode").value = postalCode;

            document.getElementById("citySelect").value = parseInt(cityId); //parseInt()


        }

    }

}

async function getCityData() {

    const response = await fetch("CityData");

    if (response.ok) {

        const json = await response.json();

        const citySelect = document.getElementById("citySelect");

        json.forEach(city => {

            let option = document.createElement("option");

            option.innerHTML = city.name;

            option.value = city.id;

            citySelect.appendChild(option);

        });

    }

}


async  function saveChanges() {

    const firstname = document.getElementById("firstname").value;
    const lastname = document.getElementById("lastname").value;
    const lineOne = document.getElementById("lineOne").value;
    const lineTwo = document.getElementById("lineTwo").value;
    const postalCode = document.getElementById("postalCode").value;
    const cityId = document.getElementById("citySelect").value;
    const currentpassword = document.getElementById("currentpassword").value;
    const newpassword = document.getElementById("newpassword").value;
    const comfirmpassword = document.getElementById("comfirmpassword").value;

//    console.log(cityId);

    const userDataObject = {
        firstName: firstname,
        lastName: lastname,
        lineOne: lineOne,
        lineTwo: lineTwo,
        postalCode: postalCode,
        cityId: cityId,
        currentPassword: currentpassword,
        newPassword: newpassword,
        confirmPassword: comfirmpassword
    };

    const userDataJSON = JSON.stringify(userDataObject);

    const response = await fetch("MyAccount", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: userDataJSON
    });

    if (response.ok) {

        const json = await response.json;


        if (json.status) {

            getUserData();

        } else {

            document.getElementById("message").innerHTML = json.message;

        }

    } else {

        document.getElementById("message").innerHTML = "Profile deatiles update failed!";

     }

}