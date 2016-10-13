function XMLDocument() {
}
XMLDocument.newDomcument = function () {
    if (window.ActiveXObject) {
        return new ActiveXObject("Microsoft.XMLDOM");
    } else {
        //infoDialog("Your browser cannot handle this script");
    }
    //TODO firefox ...
};

