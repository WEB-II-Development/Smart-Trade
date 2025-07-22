async function loadData() {

    const popup = Notification();

    const response = await fetch("LoadProductData");

    if (response.ok) {

        const json = await response.json();

        if (json.status) {

            console.log(json);

            popup.success({message: "Success"});

        } else {

//            window.location = "index.html";

            popup.error({message: "Smothing went wrong"});

        }

    } else {

//        window.location = "index.html";

        popup.error({message: "Smothing went wrong"});

    }

}