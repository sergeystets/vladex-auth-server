export default (to, from, next) => {
  // if user goes from sign-in to sign-in/otp
  if ((from.fullPath === "/sign-in" && to.fullPath === "/sign-in/otp") ||
      // or back to sign-in from sign-in/otp
      (to.fullPath === "/sign-in" && from.fullPath === "/sign-in/otp") ||
      // or he simply goes to a sign-in page
      to.fullPath === "/sign-in") {
    next()
  } else {
    next('/sign-in')
  }
}
