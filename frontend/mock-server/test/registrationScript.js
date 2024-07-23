const register = async(name = 'K') => {
    const user = {
        username: name,
        password: '123',
    }
    const response = await fetch('/api/auth/registration', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
          },
          body: JSON.stringify(user)
    });
    const msg = await response.json();
    console.log(msg)
}