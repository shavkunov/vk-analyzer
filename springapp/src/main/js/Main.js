'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import StartForm from './StartForm.js';

const App = () => (
    <MuiThemeProvider>
        <StartForm />
    </MuiThemeProvider>
);

ReactDOM.render(
    <App />,
    document.getElementById('react')
);