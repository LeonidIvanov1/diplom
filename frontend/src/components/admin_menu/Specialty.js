import React, {Component} from 'react';
import {Button, ButtonToolbar, Container, Form, Table} from "react-bootstrap";
import {Col} from "react-bootstrap/es";

class Specialty extends Component {

    command = this.props.location.pathname.slice(1);
    url = 'http://localhost:8088/library_war/FrontController?';

    constructor(props) {
        super(props);
        this.state = {
            specialty_id: "",
            name: "",
            code: "",
            parameter: "",
            description: "",
            info: true,

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
        if (this.command !== 'add_specialty') {
            this.setState({
                info: true

            });
            this.getspecialtyInfo();
        } else {
            this.setState({info: false})
        }
    };

    getspecialtyInfo = () => {
        let specialtyID = this.command.split("/")[1];
        this.setState({specialty_id: specialtyID});
        let data = {
            command: 'GET_specialty',
            specialty_id: specialtyID
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
                code: data.value.specialtyCode,
                parameter: data.value.standardParameter,
                description: data.value.description
            });
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
        console.log(this.state)
    };


    showHeader = () => {
        if (this.state.info) {
            return <div>
                <h1>Информация о специальности</h1>
            </div>
        } else {
            return <div>
                <h1>Добавление новой специальности</h1>
            </div>
        }
    };


    getBottomButtons = () => {
        if (this.state.info) {
            return <div>
                <Button onClick={(e) => {

                    let data = {
                        command: "CHANGE_specialty",
                        specialty_id: this.state.specialty_id,
                        name: this.state.name,
                        code: this.state.code,
                        parameter: this.state.parameter,
                        description: this.state.description
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
                    this.props.history.push("/specialties");
                }
                }>Изменить</Button>
                <Button variant="secondary" className="delete-user" onClick={(e) => {
                    let data = {
                        command: "DELETE_specialty",
                        specialty_id: this.state.specialty_id,
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
                    this.props.history.push("/specialties");
                }}>Удалить</Button>
            </div>
        } else {
            return <div>
                <Button onClick={(e) => {
                    let data = {
                        command: "ADD_specialty",
                        specialty_id: this.state.specialty_id,
                        name: this.state.name,
                        code: this.state.code,
                        parameter: this.state.parameter,
                        description: this.state.description
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
                    this.props.history.push("/specialties");
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
                            <Form.Label>Наименование специальноси</Form.Label>
                            <Form.Control required type="text" placeholder="Введите наименование специальности"
                                          value={this.state.name}
                                          onChange={(e) => {
                                              this.setState({name: e.target.value})
                                          }}/>
                        </Form.Group>
                    </Form.Row>


                    <Form.Row>
                        <Form.Group as={Col} controlId="fioInput">
                            <Form.Label>Код специальности</Form.Label>
                            <Form.Control required type="text" placeholder="Введите код parameter"
                                          value={this.state.code}
                                          onChange={(e) => {
                                              this.setState({code: e.target.value})
                                          }}/>
                        </Form.Group>
                    </Form.Row>

                    <Form.Row>
                        <Form.Group as={Col} controlId="fioInput">
                            <Form.Label>Параметр ФГОС</Form.Label>
                            <Form.Control required type="text" placeholder="Введите параметр ФГОС"
                                          value={this.state.parameter}
                                          onChange={(e) => {
                                              this.setState({parameter: e.target.value})
                                          }}/>
                        </Form.Group>
                    </Form.Row>


                    <Form.Row>
                        <Form.Group as={Col} controlId="fioInput">
                            <Form.Label>Описание</Form.Label>
                            <Form.Control as="textarea" rows="3" required type="text" placeholder="Введите описание"
                                          value={this.state.description}
                                          onChange={(e) => {
                                              this.setState({description: e.target.value})
                                          }}/>
                        </Form.Group>
                    </Form.Row>

                    {this.getBottomButtons()}
                </Form>
            </Container>
        );
    }
}

export default Specialty;
