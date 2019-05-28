import React, {Component} from 'react';
import Container from "react-bootstrap/Container";
import {Button, ButtonGroup, Col, Form, Table} from "react-bootstrap";


class TakenLiterature extends Component {

    command = this.props.location.pathname.slice(1);
    url = 'http://localhost:8088/library_war/FrontController?';


    constructor(props) {

        super(props);
        this.state = {
            name: '',
            role: '',
            lib_card: '',
            group: '',
            books: [],
            booksDates: [],
            user_id: ""
        }
        ;
    }


    componentDidMount() {
        let userID = window.sessionStorage.getItem('user_id');
        this.setState({get_user_info: true, user_id: userID});
        this.getUserData(userID);
        this.getUserBooks(userID);
        this.getBooksHistory(userID);
    }

    getBooksHistory(userID) {
        let data = {
            command: 'GET_BOOKS_DATE',
            user_id: userID,
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
                booksDates: data.value
            });


        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }

    getUserBooks(userID) {
        let data = {
            command: 'GET_USER_BOOKS',
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
                books: data.value
            });


        }).catch(function (error) {
            console.log('There has been a problem with your fetch operation' + error.message);
        });
    }


    getUserData(userID) {
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

    setNeedData() {
        console.log(this.state.role + " ROLE2")

        if (this.state.role === 'TEACHER') {

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
                    console.log(grID);
                    this.setState({lib_card: data.value.libCardNumber, group: grID});
                }
            ).catch(function (error) {
                console.log('There has been a problem with your fetch operation' + error.message);
            });
        }
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

    setStudentDara() {
        if (this.state.role === 'STUDENT') {
            return <div>
                <Form.Row>
                    <Form.Group as={Col} controlId="roleInput">
                        <Form.Label>Группа</Form.Label>
                        <Form.Control type="text" readonly
                                      value={this.state.group}
                        />
                    </Form.Group>

                    <Form.Group as={Col} controlId="libInput">
                        <Form.Label>Номер читательского билета</Form.Label>
                        <Form.Control type="text" readonly
                                      value={this.state.lib_card}
                        />
                    </Form.Group>
                </Form.Row>
            </div>
        }
    }

    setBookType(data) {
        switch (data) {
            case 'BOOK':
                return 'Учебное пособие';
            case 'TRAINING':
                return 'Методическое пособие'
        }
    }

    createBooks = () => {
        let books = this.state.books;
        let rows = [];
        if (books != null) {
            for (let i = 0; i < books.length; i++) {
                let row = <tr key={i + books[i].id} id={books[i].id}>
                    <td>{i + 1}</td>
                    <td>{books[i].name}</td>
                    <td>{books[i].author}</td>
                    <td>{books[i].year}</td>
                    <td>{this.setBookType(books[i].literatureType)}</td>
                    <td>{books[i].isbn}</td>
                    <td>{this.getInitialDate(books[i].id)}</td>
                    <td>{this.getEndDate(books[i])}</td>

                </tr>;
                rows.push(row);
            }
        }
        return rows;
    };

    getInitialDate(id) {
        for (let i = 0; i < this.state.booksDates.length; i++) {
            if (+this.state.booksDates[i].id === +id) {

                return new Date(this.state.booksDates[i].date).toISOString().slice(0, 10);
            }
        }
    }

    getEndDate(book) {
        for (let i = 0; i < this.state.booksDates.length; i++) {
            if (+this.state.booksDates[i].id === +book.id) {
                console.log(+book.rental * 24 * 60 * 60 * 1000);
                console.log(book.rental);
                console.log(new Date(Date.parse(this.state.booksDates[i].date) + +book.rental * 24 * 60 * 60 * 1000));
                return new Date(Date.parse(this.state.booksDates[i].date) + (+book.rental * 7 * 24 * 60 * 60 * 1000)).toISOString().slice(0, 10);
            }
        }
    }

    extendLiterature = (e) => {
        let data = {
            command: 'EXTEND_LITERATURE',
            book_id: e.target.value
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
        this.props.history.push("/readers");
    };

    returnLiterature = (e) => {
        let data = {
            command: 'RETURN_LITERATURE',
            book_id: e.target.value
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
        this.props.history.push("/readers");
    };

    render() {

        return (

            <Container fluid="true">
                <Form>
                    <Table striped bordered hover className="data-table" onClick={this.onTableClick}>
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Название</th>
                            <th>Автор</th>
                            <th>Год</th>
                            <th>Тип</th>
                            <th>ISBN</th>
                            <th>Дата выдачи</th>
                            <th>Дата сдачи</th>

                        </tr>
                        </thead>
                        <tbody>
                        {this.createBooks()}
                        </tbody>
                    </Table>
                </Form>
            </Container>
        );
    }
}

export default TakenLiterature;
