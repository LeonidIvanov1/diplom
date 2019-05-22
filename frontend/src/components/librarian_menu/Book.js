import React, {Component} from 'react';
import {Button, ButtonToolbar, Container, Form, Table} from "react-bootstrap";
import {Col} from "react-bootstrap/es";

class Book extends Component {

    command = this.props.location.pathname.slice(1);
    url = 'http://localhost:8088/library_war/FrontController?';

    constructor(props) {
        super(props);
        this.state = {
            info: true,
            name: "",
            author: "",
            isbn: "",
            year: "",
            description: "",
            rental: "",
            status: "",
            type: "",
            holder: "",
        }
    }


    addBook = (e) => {
        e.preventDefault();
        let data = {
            command: 'ADD_BOOK',
            name: this.state.name,
            author: this.state.author,
            isbn: this.state.isbn,
            year: this.state.year,
            description: this.state.description,
            rental: this.state.rental,
            status: this.state.status,
            type: this.state.type,
            holder: this.state.holder
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
        this.props.history.push("/library");
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


    componentDidMount() {
        this.getGroups();
        if (this.command !== 'add_user') {

            let book_id = this.command.split("/")[1];
            this.setState({info: true, book_id: book_id});
            let data = {
                command: 'GET_USER_INFO',
                book_id: book_id
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
        } else {
            this.setState({info: false});
        }
    }






    showheader = () => {
        if (this.state.get_user_info) {
            return <div>
                <h1>Информация об экземпляре</h1>
            </div>
        } else {
            return <div>
                <h1>Добавление нового экземпляра</h1>
            </div>
        }
    };

    showBottomButtons = () => {
        if (this.state.get_user_info) {
            return <div>
                <ButtonToolbar>
                    <Button variant="primary" type="submit" onClick={(e) => {
                        let data = {
                            command: 'CHANGE_BOOK',
                            name: this.state.name,
                            author: this.state.author,
                            isbn: this.state.isbn,
                            year: this.state.year,
                            description: this.state.description,
                            rental: this.state.rental,
                            status: this.state.status,
                            type: this.state.type,
                            holder: this.state.holder
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
                        this.props.history.push("/literature");
                    }}>
                        Изменить
                    </Button>
                    <Button variant="primary" type="submit" className="delete-user" onClick={(e) => {
                        let data = {
                            command: 'DELETE_BOOK',
                            user_id: this.state.book_id
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
                        this.props.history.push("/literature");
                    }}>
                        Удалить
                    </Button>
                </ButtonToolbar>
            </div>
        } else {
            return <div>
                <Button variant="primary" type="submit" onClick={this.addBook}>
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
                        <Form.Group as={Col} controlId="name">
                            <Form.Label>Название</Form.Label>
                            <Form.Control required type="text" placeholder="Введите название"
                                          value={this.state.name}
                                          onChange={(e) => {
                                              this.setState({name: e.target.value})
                                          }}/>
                        </Form.Group>
                    </Form.Row>


                    <Form.Row>
                        <Form.Group as={Col} controlId="author">
                            <Form.Label>Автор</Form.Label>
                            <Form.Control required type="text" placeholder="Введите автора"
                                          value={this.state.author}
                                          onChange={(e) => {
                                              this.setState({name: e.target.author})
                                          }}/>
                        </Form.Group>
                    </Form.Row>

                    <Form.Row>
                        <Form.Group as={Col} controlId="isbn">
                            <Form.Label>ISBN</Form.Label>
                            <Form.Control required type="text" placeholder="Введите ISBN"
                                          value={this.state.isbn}
                                          onChange={(e) => {
                                              this.setState({isbn: e.target.author})
                                          }}/>
                        </Form.Group>

                        <Form.Group as={Col} controlId="year">
                            <Form.Label>Год издания</Form.Label>
                            <Form.Control required type="text" placeholder="Введите год издания"
                                          value={this.state.year}
                                          onChange={(e) => {
                                              this.setState({year: e.target.author})
                                          }}/>
                        </Form.Group>

                        <Form.Group as={Col} controlId="rental">
                            <Form.Label>Время аренды (недели)</Form.Label>
                            <Form.Control required type="text" placeholder="Введите время аренды"
                                          value={this.state.rental}
                                          onChange={(e) => {
                                              this.setState({rental: e.target.author})
                                          }}/>
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>

                        <Form.Group as={Col} controlId="type">
                            <Form.Label>Тип</Form.Label>
                            <Form.Control required type="select" placeholder="Выберите тип"
                                          value={this.state.type}
                                          onChange={(e) => {
                                              this.setState({type: e.target.status})
                                          }}/>
                        </Form.Group>

                        <Form.Group as={Col} controlId="status">
                            <Form.Label>Статус</Form.Label>
                            <Form.Control required type="select" placeholder="Выберите статус"
                                          value={this.state.status}
                                          onChange={(e) => {
                                              this.setState({status: e.target.status})
                                          }}/>
                        </Form.Group>
                    </Form.Row>
                    {this.showBottomButtons()}
                </Form>
            </Container>
        );
    }
}

export default Book;
