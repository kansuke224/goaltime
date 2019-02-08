"use strict";
import $ from 'jquery';
const global = Function('return this;')();
global.jQuery = $;
import bootstrap from 'bootstrap';


$(function(){
    console.log("start")
    var tableRow = $('.table-row')

    var dat = new Date();


    var arr = $("#projectTable").data('start-time-array');

    console.log(arr);

//    for(var i = 0; i < 10; i++) {
//        if (arr[i] === "0") {
//            } else {
//                 setInterval(() => {
//                   var dat = new Date();
//
//                   var arr = arr[0];
//
//                   var timeSeconds = Math.floor((dat.getTime() - parseInt(arr)) / 1000);
//                   var hours = Math.floor(timeSeconds / 3600);
//                   var minutes = Math.floor((timeSeconds - hours * 3600) / 60);
//                   var second = timeSeconds - hours * 3600 - minutes * 60;
//
//                   $(`#${i}`).children("td").children("p").text(`${hours}:${minutes}:${seconds}`)
//                 }, 1000);
//            }
//    }

    if (arr[0] === "0") {
    } else {
         setInterval(() => {
           let dat0 = new Date();

           let arr0 = arr[0];

           let timeSeconds0 = Math.floor((dat0.getTime() - parseInt(arr0)) / 1000);
           let hours0 = Math.floor(timeSeconds0 / 3600);
           let minutes0 = Math.floor((timeSeconds0 - hours0 * 3600) / 60);
           let seconds0 = timeSeconds0 - hours0 * 3600 - minutes0 * 60;

           $(`#${0}`).children("td").children("p").text(`${hours0}：${minutes0}：${seconds0}`)
         }, 1000);
    }
    if (arr[1] === "0") {
    } else {
         setInterval(() => {
           let dat1 = new Date();

           let arr1 = arr[1];

           let timeSeconds1 = Math.floor((dat1.getTime() - parseInt(arr1)) / 1000);
           let hours1 = Math.floor(timeSeconds1 / 3600);
           let minutes1 = Math.floor((timeSeconds1 - hours1 * 3600) / 60);
           let seconds1 = timeSeconds1 - hours1 * 3600 - minutes1 * 60;

           $(`#${1}`).children("td").children("p").text(`${hours1}：${minutes1}：${seconds1}`)
         }, 1000);
    }
    if (arr[2] === "0") {
    } else {
         setInterval(() => {
           let dat2 = new Date();

           let arr2 = arr[2];

           let timeSeconds2 = Math.floor((dat2.getTime() - parseInt(arr2)) / 1000);
           let hours2 = Math.floor(timeSeconds2 / 3600);
           let minutes2 = Math.floor((timeSeconds2 - hours2 * 3600) / 60);
           let seconds2 = timeSeconds2 - hours2 * 3600 - minutes2 * 60;

           $(`#${2}`).children("td").children("p").text(`${hours2}：${minutes2}：${seconds2}`)
         }, 1000);
    }
    if (arr[3] === "0") {
    } else {
         setInterval(() => {
           let dat3 = new Date();

           let arr3 = arr[3];

           let timeSeconds3 = Math.floor((dat3.getTime() - parseInt(arr3)) / 1000);
           let hours3 = Math.floor(timeSeconds3 / 3600);
           let minutes3 = Math.floor((timeSeconds3 - hours3 * 3600) / 60);
           let seconds3 = timeSeconds3 - hours3 * 3600 - minutes3 * 60;

           $(`#${3}`).children("td").children("p").text(`${hours3}：${minutes3}：${seconds3}`)
         }, 1000);
    }
    if (arr[4] === "0") {
    } else {
         setInterval(() => {
           let dat4 = new Date();

           let arr4 = arr[4];

           let timeSeconds4 = Math.floor((dat4.getTime() - parseInt(arr4)) / 1000);
           let hours4 = Math.floor(timeSeconds4 / 3600);
           let minutes4 = Math.floor((timeSeconds4 - hours4 * 3600) / 60);
           let seconds4 = timeSeconds4 - hours4 * 3600 - minutes4 * 60;

           $(`#${4}`).children("td").children("p").text(`${hours4}：${minutes4}：${seconds4}`)
         }, 1000);
    }
    if (arr[5] === "0") {
    } else {
         setInterval(() => {
           let dat5 = new Date();

           let arr5 = arr[5];

           let timeSeconds5 = Math.floor((dat5.getTime() - parseInt(arr5)) / 1000);
           let hours5 = Math.floor(timeSeconds5 / 3600);
           let minutes5 = Math.floor((timeSeconds5 - hours5 * 3600) / 60);
           let seconds5 = timeSeconds5 - hours5 * 3600 - minutes5 * 60;

           $(`#${5}`).children("td").children("p").text(`${hours5}：${minutes5}：${seconds5}`)
         }, 1000);
    }
    if (arr[6] === "0") {
    } else {
         setInterval(() => {
           let dat6 = new Date();

           let arr6 = arr[6];

           let timeSeconds6 = Math.floor((dat6.getTime() - parseInt(arr6)) / 1000);
           let hours6 = Math.floor(timeSeconds6 / 3600);
           let minutes6 = Math.floor((timeSeconds6 - hours6 * 3600) / 60);
           let seconds6 = timeSeconds6 - hours6 * 3600 - minutes6 * 60;

           $(`#${6}`).children("td").children("p").text(`${hours6}：${minutes6}：${seconds6}`)
         }, 1000);
    }
    if (arr[7] === "0") {
    } else {
         setInterval(() => {
           let dat7 = new Date();

           let arr7 = arr[7];

           let timeSeconds7 = Math.floor((dat7.getTime() - parseInt(arr7)) / 1000);
           let hours7 = Math.floor(timeSeconds7 / 3600);
           let minutes7 = Math.floor((timeSeconds7 - hours7 * 3600) / 60);
           let seconds7 = timeSeconds7 - hours7 * 3600 - minutes7 * 60;

           $(`#${7}`).children("td").children("p").text(`${hours7}：${minutes7}：${seconds7}`)
         }, 1000);
    }
    if (arr[8] === "0") {
    } else {
         setInterval(() => {
           let dat8 = new Date();

           let arr8 = arr[8];

           let timeSeconds8 = Math.floor((dat8.getTime() - parseInt(arr8)) / 1000);
           let hours8 = Math.floor(timeSeconds8 / 3600);
           let minutes8 = Math.floor((timeSeconds8 - hours8 * 3600) / 60);
           let seconds8 = timeSeconds8 - hours8 * 3600 - minutes8 * 60;

           $(`#${8}`).children("td").children("p").text(`${hours8}：${minutes8}：${seconds8}`)
         }, 1000);
    }
    if (arr[9] === "0") {
    } else {
         setInterval(() => {
           let dat9 = new Date();

           let arr9 = arr[9];

           let timeSeconds9 = Math.floor((dat9.getTime() - parseInt(arr9)) / 1000);
           let hours9 = Math.floor(timeSeconds9 / 3600);
           let minutes9 = Math.floor((timeSeconds9 - hours9 * 3600) / 60);
           let seconds9 = timeSeconds9 - hours9 * 3600 - minutes9 * 60;9
           $(`#${9}`).children("td").children("p").text(`${hours9}：${minutes9}：${seconds9}`)
         }, 1000);
    }


//var arr = $("#projectTable").data('start-time-array');
//console.log(arr)

//`${hours}:${minutes}:${seconds}`

});
