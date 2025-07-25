//load categories and data sets

async  function LoadData() {

    const popup = new Notification();
    const response = await fetch("LoadData");

    if (response.ok) {

        const json = await response.json();

        if (json.status) {

            //console.log(json);
            document.getElementById("all-item-count").innerHTML = json.allProductCount;

            loadOptions("brand", json.brandList, "name");
            loadOptions("condition", json.qualityList, "value");
            loadOptions("color", json.colorList, "value");
            loadOptions("storage", json.storageList, "value");

            updateProductView(json);

        } else {

            popup.success({

                message: "Something went wrong"

            });
        }

    } else {

        popup.success({

            message: "Something went wrong"

        });

        //window.location = "index.html";

    }

}

//short for models and etc...

function loadOptions(prefix, dataList, property) {

    let options = document.getElementById(prefix + "-options");
    let li = document.getElementById(prefix + "-li");
    options.innerHTML = "";

    dataList.forEach(item => {

        let li_clone = li.cloneNode(true);

        if (prefix == "color") {

            li_clone.style.borderColor = "black";
            li_clone.querySelector("#" + prefix + "-a").style.backgroundColor = item[property];

        } else {

            li_clone.querySelector("#" + prefix + "-a").innerHTML = item[property];

        }

        options.appendChild(li_clone);

    });

    const all_li = document.querySelectorAll("#" + prefix + "-options li");
    all_li.forEach(list => {

        list.addEventListener("click", function () {
            all_li.forEach(y => {
                y.classList.remove("chosen"); //<li class="choosen"><a>...</a></li> remove the choosen clas here
            });                               //<li class=""><a>...</a></li>
            this.classList.add("chosen");//<li class="choose"><a>...</a></li> Add choosen class in here
        });

    });

}

//product sorting process

async function searchProduct(firstResult) {

    const popup = new Notification();

    const brand_name = document.getElementById("brand-options").querySelector(".chosen")?.querySelector("a").innerHTML; //optional chaning > access if exists
    const condition_name = document.getElementById("condition-options").querySelector(".chosen")?.querySelector("a").innerHTML; //optional chaning > access if exists
    const color_name = document.getElementById("color-options").querySelector(".chosen")?.querySelector("a").style.backgroundColor; //optional chaning > access if exists
    const storage_value = document.getElementById("storage-options").querySelector(".chosen")?.querySelector("a").innerHTML;

    //console.log(brand_name);
    //console.log(condition_name);
    //console.log(color_name);
    //console.log(storage_value);

    const price_range_start = $("#slider-range").slider("values", 0)//left
    const price_range_end = $("#slider-range").slider("values", 1)//right

    console.log(price_range_start);
    console.log(price_range_end);

    const sort_value = document.getElementById("st-sort").value;

    //console.log(sort_value);

    const data = {

        firstResult: firstResult,
        brandName: brand_name,
        conditionName: condition_name,
        colorName: color_name,
        storageValue: storage_value,
        priceStart: price_range_start,
        priceEnd: price_range_end,
        sortValue: sort_value

    };

    const dataJSON = JSON.stringify(data);

    const response = await fetch(
            "SearchProducts",
            {
                method: "POST",
                headers: {

                    "Content-Type": "application/json"

                },
                body: dataJSON
            }

    );

    if (response.ok) {

        console.log("Ok");

        const json = await response.json();

        if (json.status) {

            console.log(json);

            updateProductView(json);

            popup.success({

                message: "Product Loading Complete!"

            });

        } else {

            popup.error({

                message: "Something went wrong. Please try again"

            });

        }

    } else {

        popup.error({

            message: "Something went wrong. Please try again"

        });

    }

}

//for show products
const st_product = document.getElementById("st-product"); // product card parent node
const st_pagination_button = document.getElementById("st-pagination-button");
let current_page = 0;

function updateProductView(json) {

    const product_container = document.getElementById("st-product-container");

    product_container.innerHTML = "";

    json.productList.forEach(product => {

        let st_product_clone = st_product.cloneNode(true); // enable child nodes cloning / allow child nodes
        st_product_clone.querySelector("#st-product-a-1").href = "single-product.html?id=" + product.id;
        st_product_clone.querySelector("#st-product-img-1").src = "product-images//" + product.id + "//image1.png";

        st_product_clone.querySelector("#st-product-add-to-cart").addEventListener(
                "click", (e) => {
            addToCart(product.id, 1);
            e.preventDefault();
                });

        //append the child in to product container

        st_product_clone.querySelector("#st-product-a-2").href = "single-proudct.html?id=" + product.id;
        st_product_clone.querySelector("#st-product-title-1").innerHTML = product.title;
        st_product_clone.querySelector("#st-product-price-1").innerHTML = new Intl.NumberFormat(
                "en-US", {minimumFractionDigits: 2}).format(product.price);

        product_container.appendChild(st_product_clone);

    });

    let st_pagination_container = document.getElementById("st-pagination-container");
    st_pagination_container.innerHTML = "";
    let all_product_count = json.allProductCount;//from loadData
    let product_per_page = 6;
    let pages = Math.ceil(all_product_count / product_per_page);
    console.log(pages);

    //preview button

    if (current_page !== 0) {

        let st_pagination_button_prev_clone = st_pagination_button.cloneNode(true);
        st_pagination_button_prev_clone.innerHTML = "Prev";
        st_pagination_button_prev_clone.addEventListener(
                "click", (e) => {

            current_page--;
            searchProduct(current_page * product_per_page);

        });

        st_pagination_container.appendChild(st_pagination_button_prev_clone);

    }

    //prev page

    //pagination-buttons

    for (let i = 0; i < pages; i++) {

        let st_pagination_button_clone = st_pagination_button.cloneNode(true);
        st_pagination_button_clone.innerHTML = i + 1;

        st_pagination_button_clone.addEventListener(
                "click", (e) => {

            current_page = i;
            searchProduct(i * product_per_page);
            e.preventDefault();
        });

        if (i === Number(current_page)) {

            st_pagination_button_clone.className = "axil-btn btn-bg-primary ml--10";

        } else {

            st_pagination_button_clone.className = "axil-btn btn-bg-secondary ml--10";

        }


        st_pagination_container.appendChild(st_pagination_button_clone);

    }
    //pagination buttons

    //next button

    if (current_page !== (pages - 1)) {

        let st_pagination_button_next_clone = st_pagination_button.cloneNode(true);
        st_pagination_button_next_clone.innerHTML = "Next";

        st_pagination_button_next_clone.addEventListener(
                "click", (e) => {

            current_page++;
            searchProduct(current_page * product_per_page);
            e.preventDefault();
        });

        st_pagination_container.appendChild(st_pagination_button_next_clone);

    }

}

async function addToCart(productId, qty) {

//    console.log(productId + " " + qty);

    const popup = new Notification();

    const response = await fetch("AddToCart?prId=" + productId + "&qty=" + qty);

    console.log(response);

    if (response.ok) {

        const json = await response.json();

//        console.log(json);

        popup.success({

            message: json.message

        });

        if (json.status) {

            popup.Error({

                message: "Something went wrong.Try again"

            });

        } else {

            popup.Error({

                message: "Something went wrong.Try again"

            });

        }

    }

}