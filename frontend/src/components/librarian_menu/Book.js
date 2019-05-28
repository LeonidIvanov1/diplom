import React, {Component} from 'react';
import {Button, ButtonToolbar, Container, Form, Row, Table} from "react-bootstrap";
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
            status: 1,
            type: 1,
            holder: "",
            statuses: [],
            types: [],
            book_id: "",
            holder_name: "",
            onHolderInput: false,
            users: ""
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
        if (this.command !== 'add_book') {
            let book_id = this.command.split("/")[1];
            this.setState({info: true, book_id: book_id});
            let data = {
                command: 'GET_BOOK_INFO',
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
                    info: true,
                    name: data.value.name,
                    author: data.value.author,
                    isbn: data.value.isbn,
                    year: data.value.year,
                    description: data.value.description,
                    rental: data.value.rental,
                    status: this.getBookStatus(data.value.literatureStatus),
                    type: this.getBookType(data.value.literatureType)
                });
                this.setHolderState(data)

            }).catch(function (error) {
                console.log('There has been a problem with your fetch operation' + error.message);
            });
        } else {
            this.setState({info: false});
        }
    }

    setHolderState(data) {
        if (data.value.holder != undefined) {
            console.log(data.value.holder);
            this.setState({holder: data.value.holder.id, holder_name: data.value.holder.fullName})
        }

    }


    getBookStatus(data) {
        switch (data) {
            case 'IN_STOCK':
                return 1;
            case 'RESERVED':
                return 2;
            case 'WRITTEN_OFF':
                return 3;
            case 'OUT_OF_STOCK':
                return 4;
        }
    }

    getBookType(data) {
        switch (data) {
            case 'BOOK':
                return 1;
            case 'TRAINING':
                return 2;
        }
    }

    showheader = () => {
        if (this.state.info) {
            return <div>
                <h1>Информация об экземпляре</h1>
            </div>
        } else {
            return <div>
                <h1>Добавление нового экземпляра</h1>
            </div>
        }
    };


    setUndefinedData(data) {
        if (data == undefined) {
            return "null";
        } else return data;
    }

    showBottomButtons = () => {
        if (this.state.info) {
            return <div>
                <ButtonToolbar>
                    <Button variant="primary" type="submit" onClick={(e) => {
                        let data = {
                            command: 'CHANGE_BOOK',
                            name: this.state.name,
                            author: this.state.author,
                            isbn: this.state.isbn,
                            year: this.state.year,
                            description: this.setUndefinedData(this.state.description),
                            rental: this.state.rental,
                            status: this.state.status,
                            type: this.state.type,
                            holder: this.setUndefinedData(this.state.holder),
                            book_id: this.state.book_id
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
                        this.props.history.push("/library");
                    }}>
                        Изменить
                    </Button>
                    <Button variant="primary" type="submit" className="delete-user" onClick={(e) => {
                        let data = {
                            command: 'DELETE_BOOK',
                            book_id: this.state.book_id
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
                        this.props.history.push("/library");
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

    getHolder() {
        let data = {
            command: 'GET_USER_INFO',
            user_id: this.state.holder
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
            }).then((data) => {
            this.setState({holder_name: data.value.fullName})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    getHolderButtons() {
        if (this.state.info) {
            return <div>
                <Form.Row>
                    <Form.Group as={Col} controlId="holder">
                        <Form.Label>Держатель</Form.Label>
                        <Form.Control required type="text" placeholder="Введите название"
                                      value={this.state.holder_name}
                                      onChange={(e) => {
                                          this.setState({holder_name: e.target.value, onHolderInput: true});
                                          this.getUsersData();
                                      }}/>

                    </Form.Group>
                    <Button className="teacher-button" variant="secondary" onClick={(e) => {
                        this.setState({holder_name: "", holder: undefined, status: 1})
                    }}>Удалить</Button>
                </Form.Row>

            </div>
        }
    }

    getUsersData() {
        let data = {
            command: 'GET_USERS',
            start: 0,
            end: 1,
            search_data: this.state.holder_name
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
            this.setState({users: data.value})
        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    getHolderSearch() {
        if (this.state.holder == "" || this.state.holder == undefined) {
            return <div>
                <Table striped bordered hover className="data-table" onClick={this.onTableClick}>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>ФИО</th>
                        <th>Должность</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.createUsers()}
                    </tbody>
                </Table>
            </div>
        }
    }

    onTableClick = (e) => {
        let id = e.target.parentElement.getAttribute('id');
        this.setState({holder: id, onHolderInput: false, holder_name: this.getHolderName(id)})
    };

    getHolderName(id) {
        for (let i = 0; i < this.state.users.length; i++) {
            if (+this.state.users[i].id === +id) {
                return this.state.users[i].fullName;
            }

        }
    }

    createUsers() {
        let users = this.state.users;
        let rows = [];
        if (users != null) {
            for (let i = 0; i < users.length; i++) {
                let row = <tr key={users[i].id + i} id={users[i].id}>
                    <td>{i + 1}</td>
                    <td>{users[i].fullName}</td>
                    <td>{this.getRole(users[i].role)}</td>
                </tr>;
                rows.push(row);
            }
        }
        return rows;
    }

    getRole(role) {
        switch (role) {
            case 'ADMIN':
                return "Администратор";
            case 'LIBRARIAN':
                return "Библиотекарь";
            case 'TEACHER':
                return "Преподаватель";
            case 'STUDENT':
                return "Студент";
            default:
                return "Неопределена";
        }
    }

    render() {
        return (
            <Container>
                {this.showheader()}

                <Form>
                    <Form.Row>
                        <Form.Group as={Col} controlId="name">
                            <Form.Label>Название</Form.Label>
                            <Form.Control required type="text"
                                          placeholder="Введите ФИО держателя или его номер читательского билета"
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
                                              this.setState({author: e.target.value})
                                          }}/>
                        </Form.Group>
                    </Form.Row>

                    <Form.Row>
                        <Form.Group as={Col} controlId="isbn">
                            <Form.Label>ISBN</Form.Label>
                            <Form.Control required type="text" placeholder="Введите ISBN"
                                          value={this.state.isbn}
                                          onChange={(e) => {
                                              this.setState({isbn: e.target.value})
                                          }}/>
                        </Form.Group>

                        <Form.Group as={Col} controlId="year">
                            <Form.Label>Год издания</Form.Label>
                            <Form.Control required type="text" placeholder="Введите год издания"
                                          value={this.state.year}
                                          onChange={(e) => {
                                              this.setState({year: e.target.value})
                                          }}/>
                        </Form.Group>

                        <Form.Group as={Col} controlId="rental">
                            <Form.Label>Время аренды (неделя)</Form.Label>
                            <Form.Control required type="text" placeholder="Введите время аренды"
                                          value={this.state.rental}
                                          onChange={(e) => {
                                              this.setState({rental: e.target.value})
                                          }}/>
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>

                        <Form.Group as={Col} controlId="type">
                            <Form.Label>Тип</Form.Label>
                            <Form.Control required as="select" placeholder="Выберите тип"
                                          value={this.state.type}
                                          onChange={(e) => {
                                              this.setState({type: e.target.value})
                                          }}>
                                <option value="1">Учебное пособие</option>
                                <option value="2">Методическое указание</option>
                            </Form.Control>

                        </Form.Group>

                        <Form.Group as={Col} controlId="status">
                            <Form.Label>Статус</Form.Label>
                            <Form.Control required as="select" placeholder="Выберите статус"
                                          value={this.state.status}
                                          onChange={(e) => {
                                              this.setState({status: e.target.value})
                                          }}>


                                <option value="1">В наличии</option>
                                <option value="2">Зарезервирована</option>
                                <option value="3">Списана</option>
                                <option value="4">Нет в наличии</option>
                            </Form.Control>

                        </Form.Group>
                    </Form.Row>

                    <Form.Row>
                        <Form.Group as={Col} controlId="description">
                            <Form.Label>Описание</Form.Label>
                            <Form.Control required as="textarea" rows="3" placeholder="Введите описание"
                                          value={this.state.description}
                                          onChange={(e) => {
                                              this.setState({description: e.target.value})
                                          }}/>
                        </Form.Group>
                    </Form.Row>
                    {this.getHolderButtons()}
                    {this.getHolderSearch()}
                    {this.showBottomButtons()}
                </Form>
            </Container>
        );
    }
}

export default Book;
