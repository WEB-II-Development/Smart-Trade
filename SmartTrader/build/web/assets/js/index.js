async  function checkSessionCart() {

    const popup = new Notification();
    const response = await fetch("CheckSessionCart");

    if (response.ok) {



    } else {

        popup.error({

            message: "Something went wrong. Please try again"

        });

    }

}