import React, {Component} from 'react';
import {Button, ButtonToolbar, Container, Form, Table} from "react-bootstrap";
import {Col} from "react-bootstrap/es";

class User extends Component {

    command = this.props.location.pathname.slice(1);
    url = 'http://localhost:8088/library_war/FrontController?';

    constructor(props) {
        super(props);
        this.state = {
            fio: "",
            login: "",
            password: "",
            role: "admin",
            group: "",
            lib_card: "",
            disciplines: [],
            specialties: [],
            teacherSelectedGroup: "",
            teacherSelectedDiscipline: "",
            groupDiscipline: [],
            change: false,
            get_user_info: false,
            user_id: "",
            groupName:""

        }
    }

    getRequestData(data) {
        var formData = new FormData();
        for (var name in data) {
            formData.append(name, data[name]);
        }
        let searchParams = Object.keys(data).map((key) => {
            return encodeURIComponent(key) + '=' + encodeURIComponent(data[key]);
        }).join('&');
        return searchParams;
    }

    addUser = (e) => {
        e.preventDefault();
        let data = {
            command: 'ADD_USER',
            fio: this.state.fio,
            login: this.state.login,
            password: this.state.password,
            role: this.state.role,
            student_group: this.state.group,
            lib_card: this.state.lib_card,
            groupDiscipline: JSON.stringify(this.state.groupDiscipline)
        };
        let req = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: req
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then().catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
        this.props.history.push("/users");
    };

    getRequestData(data) {
        var formData = new FormData();
        for (var name in data) {
            formData.append(name, data[name]);
        }
        let searchParams = Object.keys(data).map((key) => {
            return encodeURIComponent(key) + '=' + encodeURIComponent(data[key]);
        }).join('&');
        return searchParams;
    }

    getGroups() {
        let data = {
            command: 'GET_GROUPS',

        };
        let req = this.getRequestData(data);
        fetch(this.url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: req
        }).then((response) => response.text())
            .then(response => {
                return JSON.parse(response);
            }).then(data => {
            this.setState({specialties: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    componentDidMount() {
        this.getGroups();
        if (this.command !== 'add_user') {

            let userID = this.command.split("/")[1];
            this.setState({get_user_info: true, user_id: userID});
            let data = {
                command: 'GET_USER_INFO',
                user_id: userID
            };

            let requestData = this.getRequestData(data);
            fetch(this.url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                },
                body: requestData
            }).then((response) => response.text())
                .then(response => {
                    return JSON.parse(response);
                }).then(data => {
                this.setState({
                    fio: data.value.fullName,
                    login: data.value.login,
                    password: data.value.password,
                    role: data.value.role,
                    user_id: data.value.id
                });
                this.setNeedData();

            }).catch(function (error) {
                console.log('There has been a problem with your fetch operation' + error.message);
            });
        }
    }

    setNeedData() {

        if (this.state.role === 'TEACHER') {
            let data = {
                command: 'GET_TEACHER_DISCIPLINES',
                user_id: this.state.user_id
            };

            let requestData = this.getRequestData(data);
            fetch(this.url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                },
                body: requestData
            }).then((response) => response.text())
                .then(response => {
                    return JSON.parse(response);
                }).then(data => {
                this.setState({
                    groupDiscipline: data.value
                });

            }).catch(function (error) {
                console.log('There has been a problem with your fetch operation' + error.message);
            });
        } else if (this.state.role === 'STUDENT') {
            let data = {
                command: 'GET_STUDENT_DATA',
                user_id: this.state.user_id
            };

            let requestData = this.getRequestData(data);
            fetch(this.url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                },
                body: requestData
            }).then((response) => response.text())
                .then(response => {
                    return JSON.parse(response);
                }).then(data => {
                    let grID = 0;
                    if (data.value.group.groupID != undefined) {
                        grID = data.value.group.groupID;
                    }
                    this.setState({lib_card: data.value.libCardNumber, group: grID});
                }
            ).catch(function (error) {
                console.log('There has been a problem with your fetch operation' + error.message);
            });
        }
    }

    getGroupsOptions() {
        let options = [];
        for (let i = 0; i < this.state.specialties.length; i++) {
            options.push(<option key={i + this.state.specialties[i].name}
                                 value={this.state.specialties[i].groupID} id={this.state.specialties[i].name}>{this.state.specialties[i].name}</option>);
        }
        return options;
    }

    DiffArrays(A, B) {
        var M = A.length, N = B.length, c = 0, C = [];
        for (var i = 0; i < M; i++) {
            var j = 0, k = 0;
            while (B[j] !== A[i] && j < N) j++;
            while (C[k] !== A[i] && k < c) k++;
            if (j === N && k === c) C[c++] = A[i];
        }
        return C;
    }

    getGroupDisciplines() {
        let options = [];


        let disciplines = [];
        let selectedDisciplines = [];
        for (let i = 0; i < this.state.groupDiscipline.length; i++) {
            if (this.state.groupDiscipline[i].group === this.getGroupName(this.state.teacherSelectedGroup)) {
                selectedDisciplines.push(this.state.groupDiscipline[i].discipline);
            }
        }



        for (let i = 0; i < this.state.specialties.length; i++) {
            if (+this.state.specialties[i].groupID === +this.state.teacherSelectedGroup) {

                disciplines = this.state.specialties[i].disciplines;
            }
        }

        let resultdis = this.DiffArrays(disciplines, selectedDisciplines);


        for (let i = 0; i < resultdis.length; i++) {

            options.push(<option key={i * 2 + resultdis[i]} value={resultdis[i]}>{resultdis[i]}</option>);
        }

        return options;
    }

    getSelectedDisciplinesTable = () => {
        let rows = [];
        for (let i = 0; i < this.state.groupDiscipline.length; i++) {
            rows.push(<tr key={i * 3 + this.state.groupDiscipline[i].group + this.state.groupDiscipline[i].discipline}>
                <td>{i + 1}</td>
                <td>{this.state.groupDiscipline[i].group}</td>
                <td>{this.state.groupDiscipline[i].discipline}</td>
                <td><Button variant="secondary" onClick={(event) => {
                    let gr = this.state.groupDiscipline;
                    gr.splice(gr.indexOf(this.state.groupDiscipline[i]), 1);
                    this.setState({groupDiscipline: gr});
                }
                }>Удалить</Button></td>
            </tr>)
        }
        return rows;
    };

    getGroupName(id) {
        let groupName;
        for (let i = 0; i <this.state.specialties.length; i++) {
            if (+this.state.specialties[i].groupID === +this.state.teacherSelectedGroup) {
                groupName = this.state.specialties[i].name;
            }
        }
        return groupName;
    }
    addGroupDiscipline = (data) => {

        if (this.state.teacherSelectedGroup !== "" && this.state.teacherSelectedDiscipline != "") {
            if (this.state.teacherSelectedDiscipline !== undefined || this.state.teacherSelectedDiscipline !== "") {

                let groupDisciplineData = this.state.groupDiscipline;
                groupDisciplineData.push({
                    group: this.getGroupName(this.state.teacherSelectedGroup),
                    discipline: this.state.teacherSelectedDiscipline
                });
                this.setState({
                    groupDiscipline: groupDisciplineData,
                    teacherSelectedGroup: "",
                    teacherSelectedDiscipline: ""
                });
            }
        }
    };

    showAddData() {
        switch (this.state.role.toLowerCase()) {
            case 'student':
                return <Form.Row>
                    <Form.Group as={Col} controlId="groupSelect">
                        <Form.Label>Группа</Form.Label>
                        <Form.Control as="select" value={this.state.group} onChange={(event) => {
                            this.setState({group: event.target.value})
                        }}>
                            {this.getGroupsOptions()}
                        </Form.Control>
                    </Form.Group>
                    <Form.Group as={Col} controlId="libCardInput">
                        <Form.Label>Номер читательского билета</Form.Label>
                        <Form.Control required value={this.state.lib_card} type="text"
                                      placeholder="Введите номер читательского билета"
                                      onChange={(event) => {
                                          this.setState({lib_card: event.target.value})
                                      }}/>
                    </Form.Group>
                </Form.Row>;
            case 'teacher':
                return <Container>
                    <Form.Row>
                        <Form.Group as={Col} controlId="groupsSelect">
                            <Form.Label>Группа</Form.Label>
                            <Form.Control as="select" onChange={(event) => {
                                this.setState({teacherSelectedGroup: event.target.value})

                            }}>
                                <option value="default">Выберите группу</option>
                                {this.getGroupsOptions()}
                            </Form.Control>
                        </Form.Group>
                        <Form.Group as={Col} controlId="disciplinesSelect">
                            <Form.Label>Дисциплина</Form.Label>
                            <Form.Control as="select" onChange={(event) => {
                                this.setState({teacherSelectedDiscipline: event.target.value})
                            }}>
                                <option value="default">Выберите дисциплину</option>
                                {this.getGroupDisciplines()}
                            </Form.Control>
                        </Form.Group>
                        <Button variant="secondary" className="teacher-button" size="sm"
                                onClick={this.addGroupDiscipline}>Добавить преподаваемую
                            дисциплину</Button>
                    </Form.Row>
                    <p>Преподаваемые дисциплины:</p>
                    <Table striped bordered className="data-table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Группа</th>
                            <th>Дисциплина</th>
                            <th>Действие</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.getSelectedDisciplinesTable()}

                        </tbody>
                    </Table>
                </Container>


        }
    }

    showheader = () => {
        if (this.state.get_user_info) {
            return <div>
                <h1>Информация о пользователе</h1>
            </div>
        } else {
            return <div>
                <h1>Добавление нового пользователя</h1>
                <p>
                    Для добавления нового пользователя, пожалуйста, заполните все поля
                </p>
            </div>
        }
    };

    showRoleSelect = () => {
        if (this.state.get_user_info) {
            let strRole;
            switch (this.state.role) {
                case 'ADMIN':
                    strRole = 'Администратор';
                    break;
                case 'TEACHER':
                    strRole = 'Преподаватель';
                    break;
                case 'LIBRARIAN':
                    strRole = 'Библиотекарь';
                    break;
                case 'STUDENT':
                    strRole = 'Студент';
                    break;

            }
            return <Form.Group as={Col} controlId="roleInput">
                <Form.Label>Роль</Form.Label>
                <Form.Control type="text" readOnly
                              value={strRole}
                />
            </Form.Group>
        } else {
            return <Form.Group as={Col} controlId="roleSelect">
                <Form.Label>Роль</Form.Label>
                <Form.Control as="select"
                              value={this.state.role}
                              onChange={(event) => {
                                  this.setState({role: event.target.value})
                              }}>
                    <option value="admin">Администратор</option>
                    <option value="student">Студент</option>
                    <option value="teacher">Преподаватель</option>
                    <option value="librarian">Библиотекарь</option>
                </Form.Control>
            </Form.Group>
        }
    };

    showBottomButtons = () => {
        if (this.state.get_user_info) {
            return <div>
                <ButtonToolbar>
                    <Button variant="primary" type="submit" onClick={(e) => {
                        let data = {
                            command: 'CHANGE_USER',
                            fio: this.state.fio,
                            password: this.state.password,
                            role: this.state.role,
                            student_group: this.state.group,
                            lib_card: this.state.lib_card,
                            groupDiscipline: JSON.stringify(this.state.groupDiscipline),
                            user_id: this.state.user_id
                        };
                        let requestData = this.getRequestData(data);
                        fetch(this.url, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                            },
                            body: requestData
                        }).then((response) => response.text())
                            .then(response => {
                                return JSON.parse(response);
                            }).catch();
                        this.props.history.push("/users");
                    }}>
                        Изменить
                    </Button>
                    <Button variant="primary" type="submit" className="delete-user" onClick={(e) => {
                        let data = {
                            command: 'DELETE_USER',
                            user_id: this.state.user_id
                        };

                        let requestData = this.getRequestData(data);
                        fetch(this.url, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                            },
                            body: requestData
                        }).then((response) => response.text())
                            .then(response => {
                                return JSON.parse(response);
                            }).catch();
                        this.props.history.push("/users");
                    }}>
                        Удалить
                    </Button>
                </ButtonToolbar>
            </div>
        } else {
            return <div>
                <Button variant="primary" type="submit" onClick={this.addUser}>
                    Добавить
                </Button>
            </div>
        }
    };

    render() {
        return (
            <Container>
                {this.showheader()}

                <Form>
                    <Form.Row>
                        <Form.Group as={Col} controlId="fioInput">
                            <Form.Label>ФИО</Form.Label>
                            <Form.Control required type="text" placeholder="Введите ФИО"
                                          value={this.state.fio}
                                          onChange={(e) => {
                                              this.setState({fio: e.target.value})
                                          }}/>
                        </Form.Group>
                    </Form.Row>

                    <Form.Row>
                        <Form.Group as={Col} controlId="loginInput">
                            <Form.Label>Логин</Form.Label>
                            <Form.Control required type="text" placeholder="Введите логин"
                                          value={this.state.login}
                                          onChange={(e) => {
                                              this.setState({login: e.target.value})
                                          }}/>
                        </Form.Group>
                        <Form.Group as={Col} controlId="passwordInput">
                            <Form.Label>Пароль</Form.Label>
                            <Form.Control required type="text"
                                          value={this.state.password}
                                          onChange={(e) => {
                                              this.setState({password: e.target.value})
                                          }} placeholder="Введите пароль"/>
                        </Form.Group>
                    </Form.Row>

                    <Form.Row>
                        {this.showRoleSelect()}
                    </Form.Row>
                    {this.showAddData()}
                    {this.showBottomButtons()}
                </Form>
            </Container>
        );
    }
}

export default User;
