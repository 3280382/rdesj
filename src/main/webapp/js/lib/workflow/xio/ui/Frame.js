function Frame(ui) {
    this.base = Component;
    if (ui) {
        this.base(ui);
    } else {
        this.base(Toolkit.newLayer());
    }

    //
    this.contentTableUI = Toolkit.newTable();
    this.ui.appendChild(this.contentTableUI);
    this.contentTableUI.cellPadding = 0;
    this.contentTableUI.cellSpacing = 0;
    this.contentTableUI.width="100%";
    this.contentTableUI.height="100%";
    this.contentTableUI.border=1;
    this.setClassName("NAME_XIO_UI_FONT NAME_XIO_UI_FRAME");
    this.contentTableUI.className = "NAME_XIO_UI_FONT NAME_XIO_UI_FRAME";
}
Frame.prototype = new Component();
Frame.prototype.toString = function () {
    return "[Component,Frame]";
};
/**
 * add component。
 * use link layout as default. each line for each new component. default height ad the same of the new component.
 */
Frame.prototype.add = function (component) {
    var componentUI = component.getUI ? component.getUI() : component;
    var height = componentUI.height ? componentUI.height : componentUI.style.height;
    var row = this.contentTableUI.insertRow(-1);
    var cell = row.insertCell(-1);
    if (height) {
        cell.height = height;
    }
    cell.appendChild(componentUI);
    return row;
};

