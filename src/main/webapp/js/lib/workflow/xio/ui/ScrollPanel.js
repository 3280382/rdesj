function ScrollPanel(ui) {
    this.base = Panel;
    this.base(ui);
    this.setClassName("NAME_XIO_UI_FONT NAME_XIO_UI_SCROLL_PANEL");
}
ScrollPanel.prototype = new Panel();
ScrollPanel.prototype.toString = function () {
    return "[Component,Panel,ScrollPanel]";
};

