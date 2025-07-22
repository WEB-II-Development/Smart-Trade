async function loadData() {

    const popup = Notification();

    const response = await fetch("LoadProductData");

    if (response.ok) {

        const json = await response.json();

        if (json.status) {

            console.log(json);

            loadOptions("brand", json.brandList, "name");
            loadOptions("condition", json.qualityList, "value");
            loadOptions("color", json.colorList, "value");
            loadOptions("storage", json.stroageList, "value");


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

function loadOptions(prefix, dataList, property) {

    let options = document.getElementById(prefix + "-options");
    let li = document.getElementById(prefix + "-li");
    options.innerHTML = "";


    dataList.forEach(item => {

//true -> juest list and others tags / false -> only list 

        let li_clone = li.cloneNode(true);

        if (prefix == "color") {

            li_clone.style.borderColor = "black";
            li_clone.querySelector("#" + prefix + "-a").style.backgroundColor = item[property];

        } else {

            li_clone.querySelector("#" + prefix + "-a").innerHTML = item[property];

        }

        options.appendChild(li_clone);

    });

    const  all_li = document.querySelectorAll("#" + prefix + "-options li");

    all_li.forEach(list => {

        list.addEventListener("click", function () {

            all_li.forEach(y => {

                y.classList.remove("chosen");

            });

            this.classList.add("chosen");

        });

    });

}