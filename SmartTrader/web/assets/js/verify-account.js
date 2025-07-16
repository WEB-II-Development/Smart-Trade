async function verifyAccount() {

    const verifycode = document.getElementById("verificationcode").value;

//    console.log(verifycode);

    const verification = {

        verificationCode: verifycode

    };

    const verificationJSON = JSON.stringify(verification);

    const response = await fetch(
            "VerifyAccount",
            {

                method: "POST",
                body: verificationJSON,
                header: {

                    "Content-Type": "application/json"

                }

            }
    );

    if (response.ok) {

        const json = await response.json();

        if (json.status) {// if true

            window.location = "index.html";

        } else {// When status fales

            if (json.message === "1") { //Email not found!!

                window.location = "sign-in.html";

            } else {

                document.getElementById("message").innerHTML = json.message;

            }

        }

    } else {

        document.getElementById("message").innerHTML = "Verification faild. Please try again!";

    }

}

