/*jshint undef: false, unused: false, indent: 2*/
/*global angular: false */

'use strict';
function Board(name, numberOfColumns) {
  return {
    name: name,
    numberOfColumns: numberOfColumns,
    columns: [],
    backlogs: []
  };
}

function Column(name) {
  return {
    name: name,
    cards: []
  };
}

function Backlog(name,userId,userName,css,img) {
  return {
    name: name,
    userId: userId,
    css: css,
    img: img,
    userName: userName,
    phases: []
  };
}

function Phase(name) {
  return {
    name: name,
    cards: []
  };
}

function Card(title, status, details, id, schecduleId, userId, scheduleName, startDate, startTime, endDate, endTime, day,
		workplaceId,workplaceName,departmentId,departmentName,employeeWorkScheduleId) {
  this.title = title;
  this.status = status;
  this.details = details;
  this.id = id;
  this.schecduleId = schecduleId;
  this.userId = userId;
  this.scheduleName = scheduleName;
  this.startDate =startDate;
  this.startTime = startTime;
  this.endDate = endDate;
  this.endTime = endTime;
  this.day = day;
  this.workplaceId = workplaceId;
  this.workplaceName = workplaceName;
  this.departmentId = departmentId;
  this.departmentName = departmentName;
  this.employeeWorkScheduleId = employeeWorkScheduleId;
  
  return this;
}
