﻿using System;

/// <summary>
/// This class is a function that executes within Evaluator. The function returns
/// a complete description of how this function works.
/// </summary>
public class Atan : Function
{
    /// <summary>
    /// Returns the name of the function - "atan".
    /// </summary>
    /// <returns> The name of this function class. </returns>
    public virtual string Name
    {
        get
        {
            return "atan";
        }
    }

    /// <summary>
    /// Executes the function for the specified argument. This method is called
    /// internally by Evaluator.
    /// </summary>
    /// <param name="evaluator">
    ///            An instance of Evaluator. </param>
    /// <param name="arguments">
    ///            A string argument that will be converted to a double value and
    ///            evaluated.
    /// </param>
    /// <returns> The arc tangent of the argument.
    /// </returns>
    /// <exception cref="FunctionException">
    ///                Thrown if the argument(s) are not valid for this function. </exception>
    //ORIGINAL LINE: public net.sourceforge.jeval.function.FunctionResult execute(final net.sourceforge.jeval.Evaluator evaluator, final String arguments) throws net.sourceforge.jeval.function.FunctionException
    public virtual FunctionResult execute(Evaluator evaluator, string arguments)
    {
        double? result = null;
        double? number = null;
        try
        {
            number = Convert.ToDouble(arguments);
        }
        catch (Exception e)
        {
            throw new FunctionException("Invalid argument.", e);
        }
        result = new double?(Math.Atan(number.Value));
        return new FunctionResult(result.ToString(), FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC);
    }
}
