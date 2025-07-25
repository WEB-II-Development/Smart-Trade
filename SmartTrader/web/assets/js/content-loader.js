function loadLayout(selector, file) {

    const element = document.querySelector(selector);

    if (!element) {

        return;

    }

    fetch(file).then(response => {

        if (response.ok) {

            return response.text();

        }

        throw new Error("Page not found");

    }).then(data => {

        element.innerHTML = data;

    }).then(error => {

        new Notification().error({message: error});

    });

}

window.addEventListener("DOMContentLoaded",function() {
    
    loadLayout("header","header.html");
    
});