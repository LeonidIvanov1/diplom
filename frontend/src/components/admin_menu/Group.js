import React, {Component} from 'react';
import {Button, ButtonToolbar, Container, Form, Table} from "react-bootstrap";
import {Col} from "react-bootstrap/es";

class Group extends Component {

    command = this.props.location.pathname.slice(1);
    url = 'http://localhost:8088/library_war/FrontController?';

    constructor(props) {
        super(props);
        this.state = {
            group_id: "",
            name: "",
            allSpecialties: [],
            allDisciplines: [],
            groupDisciplines: [],
            groupSpecialty: "",
            info: true,
            currentDis: ""
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

    getSpecialties = () => {
        let data = {
            command: 'GET_SPECIALTIES'
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
                allSpecialties: data.value
            });
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    };

    componentDidMount() {
        this.getSpecialties();
        this.setType();
        this.getDisciplines();

    }

    getDisciplines() {
        let data = {
            command: 'GET_DISCIPLINES',
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
                allDisciplines: data.value
            });
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    setType = () => {
        if (this.command !== 'add_group') {
            this.setState({
                info: true

            });
            this.getGroupInfo();
        } else {
            this.setState({info: false})
        }
    };

    getGroupInfo = () => {
        let groupID = this.command.split("/")[1];
        this.setState({group_id: groupID});
        let data = {
            command: 'GET_GROUP_INFO',
            group_id: groupID
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
                name: data.value.name,
                groupDisciplines: data.value.disciplines,
                groupSpecialty: data.value.specialty.specialtyID
            });
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });

    };

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

    setSpecialtyOptions() {
        let rows = [];
        let spec = this.state.allSpecialties;

        for (let i = 0; i < spec.length; i++) {
            let row = <option value={spec[i].specialtyID}>{spec[i].name}</option>;
            rows.push(row);
        }
        return rows;
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


    setDisciplineOptions() {
        let rows = [];
        let spec = this.DiffArrays(this.state.allDisciplines, this.state.groupDisciplines);

        for (let i = 0; i < spec.length; i++) {
            let row = <option value={spec[i]}>{spec[i]}</option>;
            rows.push(row);
        }
        return rows;
    }

    showHeader = () => {
        if (this.state.info) {
            return <div>
                <h1>Информация о группе</h1>
            </div>
        } else {
            return <div>
                <h1>Добавление новой группы</h1>
            </div>
        }
    };

    getSelectedDisciplinesTable = () => {
        let rows = [];
        let dis = this.state.groupDisciplines;
        for (let i = 0; i < dis.length; i++) {
            rows.push(<tr key={dis[i]+ i}>
                <td>{i + 1}</td>
                <td>{dis[i]}</td>
                <td><Button variant="secondary" onClick={(event) => {
                    let gr = this.state.groupDisciplines;
                    gr.splice(gr.indexOf(this.state.groupDisciplines[i]), 1);
                    this.setState({groupDiscipline: gr});
                }
                }>Удалить</Button></td>
            </tr>)
        }
        return rows;
    };

    addDisciplineGroup = () => {
        let discipline = this.state.currentDis;
        if (discipline !== "" && discipline !== "default") {
            let d = this.state.groupDisciplines;
            d.push(discipline);
            this.setState({groupDisciplines: d})
        }
    };

    getBottomButtons = () => {
        if (this.state.info) {
            return <div>
                <Button onClick={(e) => {

                    let data = {
                        command: "CHANGE_GROUP",
                        group_id: this.state.group_id,
                        name: this.state.name,
                        specialty: this.state.groupSpecialty,
                        discipline: JSON.stringify(this.state.groupDisciplines)
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
                        }).catch(function (error) {
                        console.log('There has been a problem with your fetch operation' + error.message);
                    });
                    this.props.history.push("/students_groups");
                }
                }>Изменить</Button>
                <Button variant="secondary" className="delete-user" onClick={(e) => {
                    let data = {
                        command: "DELETE_GROUP",
                        group_id: this.state.group_id,
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
                        }).catch(function (error) {
                        console.log('There has been a problem with your fetch operation' + error.message);
                    });
                    this.props.history.push("/students_groups");
                }}>Удалить</Button>
            </div>
        } else {
            return <div>
                <Button onClick={(e) => {
                    let data = {
                        command: "ADD_GROUP",
                        group_id: this.state.group_id,
                        name: this.state.name,
                        specialty: this.state.groupSpecialty,
                        discipline: JSON.stringify(this.state.groupDisciplines)
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
                        }).catch(function (error) {
                        console.log('There has been a problem with your fetch operation' + error.message);
                    });
                    this.props.history.push("/students_groups");
                }
                }>Добавить</Button>
            </div>
        }

    };

    render() {
        return (
            <Container>
                {this.showHeader()}

                <Form>
                    <Form.Row>
                        <Form.Group as={Col} controlId="fioInput">
                            <Form.Label>Наименование группы</Form.Label>
                            <Form.Control required type="text" placeholder="Введите наименование группы"
                                          value={this.state.name}
                                          onChange={(e) => {
                                              this.setState({name: e.target.value})
                                          }}/>
                        </Form.Group>
                    </Form.Row>


                    <Form.Row>
                        <Form.Group as={Col} controlId="roleSelect">
                            <Form.Label>Специальность</Form.Label>
                            <Form.Control as="select"
                                          value={this.state.groupSpecialty.specialtyID}
                                          onChange={(event) => {
                                              this.setState({groupSpecialty: event.target.value})
                                          }}>
                                {this.setSpecialtyOptions()}
                            </Form.Control>
                        </Form.Group>
                    </Form.Row>

                    <Form.Row>
                        <Form.Group as={Col} controlId="roleSelect">
                            <Form.Label>Дисциплина</Form.Label>
                            <Form.Control as="select"
                                          value={this.state.currentDis}
                                          onChange={(event) => {
                                              this.setState({currentDis: event.target.value})
                                          }}>
                                <option value="default">Выберите дисциплину</option>
                                {this.setDisciplineOptions()}
                            </Form.Control>

                        </Form.Group>
                        <Button variant="secondary" className="teacher-button" size="sm"
                                onClick={this.addDisciplineGroup}>Добавить дисциплину</Button>
                    </Form.Row>

                    <Table striped bordered className="data-table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Дисциплина</th>
                            <th>Действие</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.getSelectedDisciplinesTable()}
                        </tbody>
                    </Table>
                    {this.getBottomButtons()}
                </Form>
            </Container>
        );
    }
}

export default Group;
