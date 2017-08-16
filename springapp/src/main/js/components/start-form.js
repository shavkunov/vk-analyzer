import React from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import $ from "jquery";

const center = {
	display: "flex",
	flexDirection: "column",
	flexWrap: "wrap",
	justifyContent: "center",
	alignItems: "center",
};

class StartForm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            link: "",
            posts: 0,
            postsError: null,
            linkError: null,
        };
    }

    render() {
        // TODO : handle form input: errors, validation. Need to generate forms
        // TODO : request url to user, but need only id, so need to cut vk prefix
        return (
            <div style={center}>

                    <TextField
                        floatingLabelText="Link"
                        onChange={this.handleLinkChange.bind(this)}
                        errorText={this.state.linkError}
                        /> 

                    <TextField
                        floatingLabelText="Posts"
                        onChange={this.handlePostsChange.bind(this)}
                        errorText={this.state.postsError}
                        /> 

                    <RaisedButton label="Submit"
                                  onClick={this.validation.bind(this)}
								  style={{ marginTop: "20px" }}/>

            </div>
        );
    }

    showError = (errorDescription) => {
        if (errorDescription.type === "LINK") {
            this.setState({
                linkError: errorDescription.description
            });

            return;
        } else {
            this.setState({
                linkError: null,
            });
        }

        if (errorDescription.type === "AMOUNT") {
            this.setState({
                postsError: errorDescription.description
            });
        } else {
            this.setState({
                postsError: null,
            });
        }
    };

    validation = () => {
        let requestUrl = "http://localhost:8080/getStats";
    	// POST Request to server.
        let requestData = {
            link: this.state.link,
            posts: this.state.posts,
        };

        // TODO : bind this
        let handleError = this.showError;
        let handleTable = this.props.handleSubmit;
        $.ajax({
            type: "POST",
            url: requestUrl,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(requestData),
            dataType: "json",
            success: function(response) {
                if (response.type != "OK") {
                    handleError(response.description);
                    return;
                }

                let table = response.data;
                handleTable(table);
            }
        });
    };

    // TODO : one function
    handleLinkChange = (event) => {
        this.setState({
            link: event.target.value,
        });
    };

    handlePostsChange = (event) => {
        this.setState({
            posts: event.target.value,
        });
    };
}

export default StartForm;