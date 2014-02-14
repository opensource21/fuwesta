$(document).ready(function(){
    bsfieldConstructor('field');
    bsfieldConstructor('name');
});


function bsfieldConstructor(name) {
    $('*[bs\\:'+name+']').each(function( index ) {
        var fieldname = $(this).attr('bs:'+name);
        var input = $(this)
        var controlgroup=$('<div></div>');
        controlgroup.addClass("control-group");
        var label=$('<label></label>');
        label.addClass("control-label");
        label.append(fieldname);
        var controls = $('<div></div>');
        controls.addClass("controls");

        controlgroup.append(label);
        controlgroup.append(controls);
        input.replaceWith(controlgroup);
        controls.append(input);
    });
}
