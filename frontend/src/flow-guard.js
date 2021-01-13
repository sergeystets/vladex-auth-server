export default (to, from, next) => {
  // if user goes from login to login/otp
  if ((from.fullPath === "/login" && to.fullPath === "/login/otp") ||
      // or back to login from login/otp
      (to.fullPath === "/login" && from.fullPath === "/login/otp") ||
      // or he simply goes to a login page
      to.fullPath === "/login") {
    next()
  } else {
    next('/login')
  }
}
