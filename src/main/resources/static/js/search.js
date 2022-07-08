$("#firstZone").val(location1).prop("selected", true);
$("#firstZone").trigger("change");

if(location2 !== null) {
    $("#secondZone").val(location2).prop("selected", true);
} else {
    $("#secondZone").val("선택하세요.").prop("selected", true);
}

if(onOff === true) {
    $("#onOffLine").trigger("click");
}



