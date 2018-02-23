const myServerURL = "http://localhost:8080";

function MonitorsViewModel() {
    var self = this;

    self.monitors = ko.observableArray();
    $.get(myServerURL + "/nodes", function(data) {
        $.each(data, function(i, node) {
            if(node.state==='false'){
                node.state=false;
            }else{
                node.state=true;
            }
            self.monitors.push({
                id: ko.observable(node.id),
                name: ko.observable(node.name),
                state: ko.observable(node.state),
                description: ko.observable(node.description)
            });
        });
    });

    self.beginAdd = function() {
        $('#add').modal('show');
    }
    self.add = function(monitor){
        var monState='true';
        if(monitor.state != true){
            monState='false';
        };

        if(monitor.name.length===0) {
            monitor.name = ' ';
        };
        if(monitor.description.length===0) {
            monitor.description = ' ';
        };
        var myData = {
            'name': monitor.name,
            'description': monitor.description,
            'state': monState
        };

        $.ajax({
            url: myServerURL + '/nodes/',
            method: 'POST',
            data: JSON.stringify(myData),
            contentType: 'application/json',
            success: function(result) {
                self.monitors.push({
                    id : ko.observable(result),
                    name: ko.observable(monitor.name),
                    description: ko.observable(monitor.description),
                    state: ko.observable(monitor.state)
                });
            },
            error: function(request,msg,error) {
                alert(msg);
            }
        });
    }

    self.beginEdit = function(monitor) {
        editMonitorViewModel.setMonitor(monitor);
        $('#edit').modal('show');
    }
    self.edit = function(monitor, newmonitor) {
        self.updateMonitor(monitor, newmonitor);
    }

    self.updateMonitor = function(monitor, newmonitor) {
        var monState='true';
        if(newmonitor.state != true){
            monState='false';
        }
        var i = self.monitors.indexOf(monitor);
        var myData = {
            'name': newmonitor.name,
            'description': newmonitor.description,
            'state': monState
        };
        $.ajax({
            url: myServerURL + '/nodes/' + monitor.id(),
            method: 'PUT',
            data: JSON.stringify(myData),
            contentType: 'application/json',
            success: function(result) {
                self.monitors()[i].name(newmonitor.name);
                self.monitors()[i].description(newmonitor.description);
                self.monitors()[i].state(newmonitor.state);
            },
            error: function(request,msg,error) {
                alert(msg);
            }
        });
    }

    self.remove = function(monitor) {
        //var i = self.monitors.indexOf(monitor);
        var i = monitor.id();
        $.ajax({
            url: myServerURL + '/nodes/' + i,
            method: 'DELETE',
            contentType: 'application/json',
            success: function(result) {
                self.monitors.remove(monitor);
            },
            error: function(request,msg,error) {
                alert(msg);
            }
        });

    }

    self.stateOff = function(monitor) {
        var i = self.monitors.indexOf(monitor);
        var idToSend = monitor.id();
        console.log(idToSend);
        var myData = {
            'name': monitor.name(),
            'description':monitor.description(),
            'state': 'false'
        };
        $.ajax({
            url: myServerURL + '/nodes/' + idToSend,
            method: 'PUT',
            data: JSON.stringify(myData),
            contentType: 'application/json',
            success: function(result) {
                self.monitors()[i].state(false);
            },
            error: function(request,msg,error) {
                alert(msg);
            }
        });
    }

    self.stateOn = function(monitor) {
        var i = self.monitors.indexOf(monitor);
        var idToSend = monitor.id();
        console.log(idToSend);
        var myData = {
            'name': monitor.name(),
            'description':monitor.description(),
            'state': 'true'
        };

        $.ajax({
            url: myServerURL + '/nodes/' + idToSend,
            method: 'PUT',
            data: JSON.stringify(myData),
            contentType: 'application/json',
            success: function(result) {
                self.monitors()[i].state(true);
            },
            error: function(request,msg,error) {
                alert(msg);
            }
        });
    }

    self.beginLogin = function() {
        $('#login').modal('show');
    }
    self.login = function(username, password) {
        if(username == "nick" && password == "test") {
            alert("Login Success");
        } else {
            if(alert('Login Failure')){}
            else{    window.location.reload();}
        }
    }

    self.beginLogin();
}

function AddMonitorViewModel() {
    var self = this;
    self.id = ko.observable();
    self.name = ko.observable();
    self.description = ko.observable();
    self.state = ko.observable();

    self.addMonitor = function() {
        $('#add').modal('hide');
        monitorsViewModel.add({
            id: self.id(),
            name: self.name(),
            description: self.description(),
            state: self.state()
        });
        self.id("");
        self.name("");
        self.description("");
        self.state(false);
    }
}

function EditMonitorViewModel() {
    var self = this;
    self.id = ko.observable();
    self.name = ko.observable();
    self.description = ko.observable();
    self.state = ko.observable();

    self.editMonitor = function() {
        $('#edit').modal('hide');

        monitorsViewModel.edit(self.monitor, {
            id: self.id(),
            name: self.name(),
            description: self.description(),
            state: self.state()
        });
    }

    self.setMonitor = function(monitor) {
        self.monitor = monitor;
        self.id(monitor.id());
        self.name(monitor.name());
        self.description(monitor.description());
        self.state(monitor.state());

    }

}

function LoginViewModel() {
    var self = this;
    self.username = ko.observable();
    self.password = ko.observable();

    self.submitLogin = function() {
        $('#login').modal('hide');
        monitorsViewModel.login(self.username(), self.password());
    }
}

var monitorsViewModel = new MonitorsViewModel();
var addMonitorsViewModel = new AddMonitorViewModel();
var editMonitorViewModel = new EditMonitorViewModel();
var loginViewModel = new LoginViewModel();
ko.applyBindings(monitorsViewModel, $('#main')[0]);
ko.applyBindings(addMonitorsViewModel, $('#add')[0]);
ko.applyBindings(editMonitorViewModel, $('#edit')[0]);
ko.applyBindings(loginViewModel, $('#login')[0]);

