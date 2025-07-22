var modelList;

async  function loadProductData() {

    const response = await fetch("LoadProductData");

    if (response.ok) {

        const json = await response.json();

        if (json.status) {// if true

            loadSelect("brand", json.brandList, "name");
            modelList = json.modelList;
//            loadSelect("model", json.modelList, "name");
            loadSelect("stroge", json.stroageList, "value");
            loadSelect("color", json.colorList, "value");
            loadSelect("condition", json.qualityList, "value");

        } else {// When status fales           

            document.getElementById("message").innerHTML = "Unable to load product data! Please try again later";

        }

    } else {

        document.getElementById("message").innerHTML = "Unable to load product data! Please try again later";

    }

}

function loadSelect(selectId, list, property) {

    const select = document.getElementById(selectId);

    list.forEach(item => {

        const option = document.createElement("option");

        option.value = item.id;

        option.innerHTML = item[property];

        select.appendChild(option);

    });

}

function loadModels() {

    const brandId = document.getElementById("brand").value;

    const modelSelect = document.getElementById("model");
    modelSelect.length = 1;

    modelList.forEach(item => {

        if (item.brand.id == brandId) { // == -> only value check , === -> only value and datatype check

            const option = document.createElement("option");

            option.value = item.id;

            option.innerHTML = item.name;

            modelSelect.appendChild(option);

        }

    });

}

async function saveProduct() {

    const brandId = document.getElementById("brand").value;
    const modelId = document.getElementById("model").value;
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;
    const strogeId = document.getElementById("stroge").value;
    const colorId = document.getElementById("color").value;
    const conditionId = document.getElementById("condition").value;
    const price = document.getElementById("price").value;
    const qty = document.getElementById("qty").value;

    const image1 = document.getElementById("img1").files[0];
    const image2 = document.getElementById("img2").files[0];
    const image3 = document.getElementById("img3").files[0];

    const form = new FormData();
    form.append("brandId", brandId);
    form.append("modelId", modelId);
    form.append("title", title);
    form.append("description", description);
    form.append("strogeId", strogeId);
    form.append("colorId", colorId);
    form.append("conditionId", conditionId);
    form.append("price", price);
    form.append("qty", qty);
    form.append("image1", image1);
    form.append("image2", image2);
    form.append("image3", image3);

    const response = await fetch(
            "SaveProduct",
            {

                method: "POST",
                body: form
            }
    );

    const popup = Notification();

    if (response.ok) {

        const json = await response.json();

        if (json.status) { //true -> Success

        } else { //false -> Error

            if (json.message === "Please sign in!") {

                window.location = "sign-in.html";

            } else {

                popup.error({message: json.message});

            }

        }

    } else {
    }

}