import React, {Component} from 'react';
import {Button, ButtonToolbar, Container, Form, Table} from "react-bootstrap";
import {Col} from "react-bootstrap/es";

class Discipline extends Component {

    command = this.props.location.pathname.slice(1);
    url = 'http://localhost:8088/library_war/FrontController?';

    constructor(props) {
        super(props);
        this.state = {
            discipline_id: "",
            name: "",
            info: true
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


    componentDidMount() {
        this.setType();

    }

    setType = () => {
        if (this.command !== 'add_discipline') {
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
        this.setState({discipline_id: groupID});
        let data = {
            command: 'GET_DISCIPLINE',
            discipline_id: groupID
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


    showHeader = () => {
        if (this.state.info) {
            return <div>
                <h1>Информация о дисциплине</h1>
            </div>
        } else {
            return <div>
                <h1>Добавление новой дисциплины</h1>
            </div>
        }
    };


    getBottomButtons = () => {
        if (this.state.info) {
            return <div>
                <Button onClick={(e) => {

                    let data = {
                        command: "CHANGE_DISCIPLINE",
                        discipline_id: this.state.discipline_id,
                        name: this.state.name,
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
                    this.props.history.push("/disciplines");
                }
                }>Изменить</Button>
                <Button variant="secondary" className="delete-user" onClick={(e) => {
                    let data = {
                        command: "DELETE_DISCIPLINE",
                        discipline_id: this.state.discipline_id,
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
                    this.props.history.push("/disciplines");
                }}>Удалить</Button>
            </div>
        } else {
            return <div>
                <Button onClick={(e) => {
                    let data = {
                        command: "ADD_DISCIPLINE",
                        name: this.state.name,
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
                    this.props.history.push("/disciplines");
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
                            <Form.Label>Наименование дисциплины</Form.Label>
                            <Form.Control required type="text" placeholder="Введите наименование дисциплины"
                                          value={this.state.name}
                                          onChange={(e) => {
                                              this.setState({name: e.target.value})
                                          }}/>
                        </Form.Group>
                    </Form.Row>

                    {this.getBottomButtons()}
                </Form>
            </Container>
        );
    }
}

export default Discipline;
